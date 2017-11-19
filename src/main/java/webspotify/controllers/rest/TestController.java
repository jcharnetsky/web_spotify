package webspotify.controllers.rest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.models.media.Album;
import webspotify.models.media.Concert;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.repo.SongCollectionRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.types.GenreType;

/**
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
  @Autowired
  private SongRepository songRepo;
  @Autowired
  private SongCollectionRepository collectionRepo;

  @GetMapping("/")
  public String runTest() {
    if (!userRepo.findByEmail("user@yahoo.com").isEmpty()) {
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

    Song dummySong1 = new Song();
    dummySong1.setGenre(GenreType.POP);
    dummySong1.setIsBanned(false);
    dummySong1.setIsPublic(true);
    dummySong1.setOwner(a);
    dummySong1.setTitle("Song Title");
    dummySong1.setTrackLength(10);

    Song dummySong2 = new Song();
    dummySong2.setGenre(GenreType.ROCK);
    dummySong2.setIsBanned(false);
    dummySong2.setIsPublic(true);
    dummySong2.setOwner(a);
    dummySong2.setTitle("Here come dat boi");
    dummySong2.setTrackLength(120);

    Playlist pl = new Playlist();
    pl.setTitle("Cool Playlist");
    pl.setBanned(false);
    pl.setPublic(true);
    pl.setGenre(GenreType.POP);
    pl.setCollaborative(true);
    pl.setDescription("This is a playlist");
    pl.getSongs().add(dummySong1);
    pl.setOwner(u);
    pl.incrementFollowerCount();

    Album al = new Album();
    al.setTitle("Cool Playlist");
    al.setBanned(false);
    al.setPublic(true);
    al.setGenre(GenreType.POP);
    al.setOwner(a);

    String content = "";
    try {
      Path p = Paths.get("db/horse.mp3");
      content = new String(Files.readAllBytes(p));
    } catch (Exception e) {
      e.printStackTrace();
    }
    dummySong1.setAudio(content.getBytes());

    userRepo.save(u);
    artistRepo.save(a);
    collectionRepo.save(al);
    dummySong1.setAlbum(al);
    dummySong2.setAlbum(al);
    songRepo.save(dummySong1);
    songRepo.save(dummySong2);
    concertRepo.save(c);
    collectionRepo.save(pl);

    a.getFollowedPlaylists().add(pl);
    artistRepo.save(a);

    return "test data inputted.";
  }
}
