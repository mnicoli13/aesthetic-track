package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.*;
import IngDelSw.nicoli.repository.*;
import IngDelSw.nicoli.services.PhotosService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhotosServiceUnitTests {

    @Mock
    private PhotosRepository photosRepository;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private LocalizationViewRepository localizationViewRepository;

    @InjectMocks
    private PhotosService photosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test 1:savePhotoTest")
    public void savePhotoTest() throws Exception {
        Integer visitId = 1;
        Integer localizationViewId = 2;
        String base64Header = "data:image/jpeg;base64,";
        String base64Content = Base64.getEncoder().encodeToString("fake_image_content".getBytes());
        String base64File = base64Header + base64Content;

        Visit visit = new Visit();
        visit.setId(visitId);

        LocalizationView localizationView = new LocalizationView();
        localizationView.setId(localizationViewId);

        Photo savedPhoto = new Photo();
        savedPhoto.setId(1);
        savedPhoto.setImage("uploads/fake_image.jpg");
        savedPhoto.setVisit(visit);
        savedPhoto.setLocalizationView(localizationView);

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(localizationViewRepository.findById(localizationViewId)).thenReturn(Optional.of(localizationView));
        when(photosRepository.save(any(Photo.class))).thenReturn(savedPhoto);

        Photo result = photosService.save(base64File, localizationViewId, visitId);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1);
        Assertions.assertThat(result.getImage()).isEqualTo("uploads/fake_image.jpg");
        Assertions.assertThat(result.getVisit().getId()).isEqualTo(visitId);

        verify(visitRepository, times(1)).findById(visitId);
        verify(localizationViewRepository, times(1)).findById(localizationViewId);
        verify(photosRepository, times(1)).save(any(Photo.class));
    }

    @Test
    @DisplayName("Test 2: getPhotosByVisitIdTest")

    public void getPhotosByVisitIdTest() {
        Integer visitId = 1;
        Visit visit = new Visit();
        visit.setId(visitId);

        Photo photo1 = new Photo();
        photo1.setId(1);
        photo1.setImage("uploads/photo1.jpg");

        Photo photo2 = new Photo();
        photo2.setId(2);
        photo2.setImage("uploads/photo2.jpg");

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(photosRepository.findByVisit(visit)).thenReturn(List.of(photo1, photo2));

        List<Photo> photos = photosService.getPhotosByVisitId(visitId);

        Assertions.assertThat(photos).hasSize(2);
        Assertions.assertThat(photos.get(0).getImage()).isEqualTo("uploads/photo1.jpg");
        Assertions.assertThat(photos.get(1).getImage()).isEqualTo("uploads/photo2.jpg");

        verify(visitRepository, times(1)).findById(visitId);
        verify(photosRepository, times(1)).findByVisit(visit);
    }

    @DisplayName("Test 3: savePhotoInvalidVisitTest")
    @Test
    public void savePhotoInvalidVisitTest() {
        Integer visitId = 1;
        Integer localizationViewId = 2;
        String base64File = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString("fake_image_content".getBytes());

        when(visitRepository.findById(visitId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> photosService.save(base64File, localizationViewId, visitId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Visit not found with id " + visitId);

        verify(visitRepository, times(1)).findById(visitId);
        verifyNoInteractions(localizationViewRepository, photosRepository);
    }

    @Test
    public void savePhotoInvalidLocalizationViewTest() {
        Integer visitId = 1;
        Integer localizationViewId = 2;
        String base64File = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString("fake_image_content".getBytes());

        Visit visit = new Visit();
        visit.setId(visitId);

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(localizationViewRepository.findById(localizationViewId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> photosService.save(base64File, localizationViewId, visitId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LocalizationView not found with id " + localizationViewId);

        verify(visitRepository, times(1)).findById(visitId);
        verify(localizationViewRepository, times(1)).findById(localizationViewId);
        verifyNoInteractions(photosRepository);
    }
}
