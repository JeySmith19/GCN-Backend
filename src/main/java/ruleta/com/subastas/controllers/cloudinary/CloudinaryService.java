package ruleta.com.subastas.controllers.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Crear / Subir una imagen
     */
    public Map<String, String> uploadFile(MultipartFile file, String folder) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", folder)
        );
        // Devuelve secure_url y public_id
        return Map.of(
                "url", uploadResult.get("secure_url").toString(),
                "public_id", uploadResult.get("public_id").toString()
        );
    }

    /**
     * Eliminar imagen usando public_id
     */
    public boolean deleteFile(String publicId) throws IOException {
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return "ok".equals(result.get("result")); // true si se borró
    }

    /**
     * Actualizar imagen: borra la anterior y sube la nueva
     */
    public Map<String, String> updateFile(String oldPublicId, MultipartFile newFile, String folder) throws IOException {
        // Primero borramos la imagen vieja
        deleteFile(oldPublicId);
        // Subimos la nueva
        return uploadFile(newFile, folder);
    }

    /**
     * Listar imágenes de una carpeta (opcional)
     */
    public Map listFiles(String folder) {
        try {
            Map result = cloudinary.api().resources(ObjectUtils.asMap(
                    "type", "upload",
                    "prefix", folder
            ));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(); // Devuelve un mapa vacío si falla
        }
    }
}
