package webspotify.controllers.rest;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.models.media.Concert;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.repo.UserRepository;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ArtistRepository artistRepo;
    @Autowired
    private ConcertRepository concertRepo;

    @GetMapping("/")
    public String runTest() {
        if(!userRepo.findByEmail("user@yahoo.com").isEmpty()) {
            return "Test Data has already been inputted";
        }
        
        User u = new User();
        u.setAddress("TestAddress");
        u.setBanned(false);
        u.setBirthdate(new Date());
        u.setEmail("user@yahoo.com");
        u.setImage("");
        u.setName("TestName");
        u.setPublic(true);
        u.createSecurePassword("password");
        u.setIsPremium(true);

        Artist a = new Artist();
        a.setAddress("TestAddress");
        a.setBanned(false);
        a.setBirthdate(new Date());
        a.setEmail("artist@yahoo.com");
        a.setImage("");
        a.setName("TestName");
        a.setPublic(true);
        a.setIsPremium(true);
        a.createSecurePassword("password");
        a.setAbout("THis is a test bio");
        a.setStageName("Mary Doe");
        a.setStageName("John Doe");
        
        Concert c = new Concert();
        c.setBanned(false);
        c.setIsPublic(true);
        c.setPublic(true);
        c.setVenueName("Cool Concert");
        c.setConcertDate(new Date());
        c.setConcertURL("www.google.com");
        c.getArtists().add(a);
        
        userRepo.save(u);
        artistRepo.save(a);
        concertRepo.save(c);

        
        return "test data inputted.";
    }
}
