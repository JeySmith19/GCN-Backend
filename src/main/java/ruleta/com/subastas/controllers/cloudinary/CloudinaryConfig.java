package ruleta.com.subastas.controllers.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dutucd3bi",
                "api_key", "951229193823677",
                "api_secret", "eJcjkwP2b2BN4GPFmRGmuxLaRDk",
                "secure", true
        ));
    }
}
