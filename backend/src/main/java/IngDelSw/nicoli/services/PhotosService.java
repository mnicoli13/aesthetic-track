package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.LocalizationView;
import IngDelSw.nicoli.model.Photo;
import IngDelSw.nicoli.model.Visit;
import IngDelSw.nicoli.repository.LocalizationViewRepository;
import IngDelSw.nicoli.repository.PhotosRepository;
import IngDelSw.nicoli.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.List;


@Service
public class PhotosService {

    private static final String UPLOAD_DIR = "uploads/";


    private final PhotosRepository photosRepository;
    private final VisitRepository visitRepository;
    private final LocalizationViewRepository localizationViewRepository;

    public PhotosService(VisitRepository visitRepository, PhotosRepository photosRepository, LocalizationViewRepository localizationViewRepository) {
        this.visitRepository = visitRepository;
        this.photosRepository = photosRepository;
        this.localizationViewRepository = localizationViewRepository;
    }

    public Photo save(String base64File, Integer localizationViewId, Integer visitId ) {
        try {
            // Verifica che la visita esista
            Visit visit = visitRepository.findById(visitId)
                    .orElseThrow(() -> new IllegalArgumentException("Visit not found with id " + visitId));

            // Verifica che la LocalizationView esista
            LocalizationView localizationView = localizationViewRepository.findById(localizationViewId)
                    .orElseThrow(() -> new IllegalArgumentException("LocalizationView not found with id " + localizationViewId));

            // Decodifica il file Base64
            String[] parts = base64File.split(",");
            String fileExtension = determineFileExtension(parts[0]); // Determina il tipo di file
            byte[] fileBytes = Base64.getDecoder().decode(parts[1]);

            // Genera un nome unico per il file
            String fileName = UUID.randomUUID() + "." + fileExtension;
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // Crea la directory se non esiste
            Path directoryPath = path.getParent();
                if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
                }


            // Scrivi il file sul file system
            Files.write(path, fileBytes);

            // Crea l'oggetto Photo e salva nel database
            Photo photo = new Photo();
            photo.setImage(path.toString());
            photo.setVisit(visit);
            photo.setLocalizationView(localizationView);

            return photosRepository.save(photo);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public List<Photo> getPhotosByVisitId(Integer visitId) {
        // Trova la visita tramite visitId
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id " + visitId));

        return photosRepository.findByVisit(visit);
    }

    public String getPhotoBase64ByUrl(String imageUrl) {

        try {
            // Recupera il percorso del file dalla URL
            Path filePath = Path.of(imageUrl);

            // Controlla se il file esiste
            if (!Files.exists(filePath)) {
                throw new IllegalArgumentException("File not found at " + imageUrl);
            }

            // Leggi il file in byte
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Determina il MIME type per costruire il prefisso Base64
            String mimeType = Files.probeContentType(filePath);

            // Codifica i byte in Base64
            String base64File = Base64.getEncoder().encodeToString(fileBytes);

            // Aggiungi il prefisso Base64
            return "data:" + mimeType + ";base64," + base64File;
        } catch (IOException e) {
            throw new RuntimeException("Could not retrieve the file. Error: " + e.getMessage());
        }
    }

    /**
     * Determina l'estensione del file dal prefisso base64
     */
    private String determineFileExtension(String base64Header) {
        if (base64Header.contains("image/jpeg")) {
            return "jpg";
        } else if (base64Header.contains("image/png")) {
            return "png";
        } else if (base64Header.contains("image/gif")) {
            return "gif";
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + base64Header);
        }
    }


}

