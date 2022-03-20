package rs.ac.uns.ftn.isa.fisherman.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {
    private final Logger logger= LoggerFactory.getLogger(FirebaseInitialize.class);
    private static final String CLOUD= "./isafisherman-firebase.json";

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount=new FileInputStream(CLOUD);
        try {
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build(); //NOSONAR
            FirebaseApp.initializeApp(options);
        }catch (Exception e){
            logger.error(e.toString());
        }finally {
            serviceAccount.close(); 
        }

    }
}
