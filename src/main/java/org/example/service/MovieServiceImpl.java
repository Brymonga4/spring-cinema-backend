package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.util.YoutubeUrlConverter;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.MovieDTO;
import org.example.exception.Exceptions;
import org.example.mapper.MovieMapper;
import org.example.model.Movie;
import org.example.repository.MovieRepository;
import org.example.util.UploadConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.util.MovieTitleUtil.getFileExtension;
import static org.example.util.MovieTitleUtil.sanitizeTitle;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final UploadConfig uploadConfig;

    public MovieServiceImpl(MovieRepository repository, UploadConfig uploadConfig) {
        this.repository = repository;
        this.uploadConfig = uploadConfig;
    }
    @Override
    public List<MovieDTO> findAll() {

        return this.repository.findAll().stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findRecentMovies() {
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        return this.repository.findRecentMovies(twoWeeksAgo).stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findMovieListing() {

        LocalDateTime endDate = LocalDateTime.now().plusDays(14);

        return this.repository.findMovieListingUntilenDate(endDate).stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<MovieDTO> findAllByYear(Integer year) {
        return null;
    }

    @Override
    public Optional<MovieDTO> findById(Long id) {

        return this.repository.findById(id)
                .map(MovieMapper::toDTO);

    }

    @Override
    public Optional<Movie> findMovieByTitle(String name) {
        return this.repository.findMovieByTitle(name);
    }

    @Override
    public MovieDTO save(Movie movie) {
        MovieDTO movieDTO;
        if (repository.existsByTitleAndOrigTitle(movie.getTitle(), movie.getOrigTitle())) {
            System.out.println(movie.getTitle() + movie.getOrigTitle());
            throw new Exceptions.MovieNotFoundException("Ya existe una peli con ese titulo");
        }
        else {
            String urlYoutube = YoutubeUrlConverter.convertToEmbedUrl(movie.getTrailer());
            movie.setTrailer(urlYoutube);
             movieDTO = MovieMapper.toDTO(this.repository.save(movie));
        }

        return movieDTO;
    }


    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
    }


    @Override
    @Transactional
    public MovieDTO update(Movie movie) {
        //Lo bloqueamos primero
        this.repository.findAndLockById(movie.getId());
        return this.save(movie);
    }

    @Override
    public Movie handleFileUpload(Movie movie, MultipartFile file) {
        Path uploadPath = Paths.get(uploadConfig.getUploadDir());

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                throw new Exceptions.EmptyFileNameTitleException("");
            }

            String fileName = sanitizeTitle(movie.getTitle()) + getFileExtension(file.getOriginalFilename());

            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Thumbnails.of(file.getInputStream())
                    .size(386, 546)
                    .toFile(filePath.toFile());

            movie.setImage(fileName);

            return movie;
        } catch (IOException e) {
            throw new Exceptions.FileErrorException(e.getMessage());
        }
    }

    @Override
    public Movie handleMultipleFileUpload(Movie movie, MultipartFile coverFile, MultipartFile releaseFile) {
        Path uploadPath = Paths.get(uploadConfig.getUploadDir());

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (coverFile.getOriginalFilename() == null || coverFile.getOriginalFilename().isEmpty()) {
                if (releaseFile.getOriginalFilename() == null || releaseFile.getOriginalFilename().isEmpty())
                    throw new Exceptions.EmptyFileNameTitleException("");
            }


            String coverFileName = sanitizeTitle(movie.getTitle()) + getFileExtension(coverFile.getOriginalFilename());
            String releaseFileName = "release-"+coverFileName;

            Path coverFileNamePath = uploadPath.resolve(coverFileName);
            Path releaseFileNamePath = uploadPath.resolve(releaseFileName);

            if (Files.exists(coverFileNamePath)) {
                Files.delete(coverFileNamePath);
            }
            if (Files.exists(releaseFileNamePath)) {
                Files.delete(releaseFileNamePath);
            }

            Thumbnails.of(coverFile.getInputStream())
                    .size(386, 546)
                    .toFile(coverFileNamePath.toFile());


            Thumbnails.of(releaseFile.getInputStream())
                    .size(1280, 720)
                    .toFile(releaseFileNamePath.toFile());

            movie.setImage(coverFileName);

            return movie;
        } catch (IOException e) {
            throw new Exceptions.FileErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public MovieDTO uptadeMovieAndCover(Movie movie, MultipartFile file){

        Movie movieToUpdate = this.handleFileUpload(movie, file);

        return this.update(movieToUpdate);
    }




}
