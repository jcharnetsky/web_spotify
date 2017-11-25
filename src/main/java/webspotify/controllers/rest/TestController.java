package webspotify.controllers.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import webspotify.models.media.Album;
import webspotify.models.media.Concert;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.AlbumRepository;
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
  @Autowired
  private AlbumRepository albumRepo;
  @Autowired
  private ResourceLoader resourceLoader;

  @GetMapping("/loadData")
  public String loadData() throws IOException, ParseException {
    Resource resource = resourceLoader.getResource("url:http://localhost:8080/scripts/result.json");
    InputStream inputStream = resource.getInputStream();
    String jsonTxt = new Scanner(inputStream).useDelimiter("\\A").next();

    JSONObject obj = new JSONObject(jsonTxt);
    //First get artists
    JSONArray artists = obj.getJSONArray("artists");
    JSONArray albums = obj.getJSONArray("albums");
    JSONArray songs = obj.getJSONArray("songs");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    for (int i = 0; i < artists.length(); i++) {
      Artist artist = new Artist();
      JSONObject artistJson = artists.getJSONObject(i);

      artist.setId(artistJson.getInt("id"));
      artist.setAbout("");
      artist.setAddress(artistJson.getString("address"));
      artist.setBanned(false);
      artist.setPublic(true);
      artist.setBirthdate(dateFormat.parse(artistJson.getString("birthday")));
      artist.setIsPremium(true);
      artist.setName(artistJson.getString("name"));
      artist.setStageName(artist.getName());
      artist.setEmail(artist.getName() + "@yahoo.com");
      artist.setPassword("");
      artist.setPasswordSalt("");
      List<String> aliases = new ArrayList<String>();
      JSONArray aliasesInJson = artistJson.getJSONArray("aliases");
      for (int j = 0; j < aliasesInJson.length(); j++) {
        aliases.add(aliasesInJson.getString(j));
      }
      artist.setAliases(aliases);
      artist.setHasImage(false);
      userRepo.save(artist);
    }

    for (int i = 0; i < albums.length(); i++) {
      JSONObject albumObject = albums.getJSONObject(i);
      Album album = new Album();
      album.setBanned(false);
      album.setPublic(true);
      album.setGenre(GenreType.POP);
      album.setTitle(albumObject.getString("title"));
      Artist artist = artistRepo.findOne(albumObject.getInt("artistId"));
      album.setOwner(artist);
      collectionRepo.save(album);
    }

    for (int i = 0; i < songs.length(); i++) {
      JSONObject songObject = songs.getJSONObject(i);
      Song song = new Song();
      
      Album album = albumRepo.findOne(songObject.getInt("albumId"));
      Artist artist = artistRepo.findOne(songObject.getInt("artistId"));

      song.setAlbum(album);
      song.setOwner(artist);
      song.setBanned(false);
      song.setPublic(true);
      song.setGenre(GenreType.POP);
      song.setHasAudio(false);
      song.setTitle(songObject.getString("title"));
      song.setAlbum(album);
      song.setTrackLength(songObject.getInt("length"));
      
      songRepo.save(song);
    }

    return jsonTxt;
  }

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
    u.setHasImage(false);
    u.setName("TestName");
    u.setPublic(true);
    u.createSecurePassword("password");
    u.setIsPremium(true);

    Artist a = new Artist();
    a.setAddress("TestAddress");
    a.setBanned(false);
    a.setBirthdate(new Date());
    a.setEmail("artist@yahoo.com");
    a.setHasImage(false);
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
    dummySong1.setHasAudio(false);

    Song dummySong2 = new Song();
    dummySong2.setGenre(GenreType.ROCK);
    dummySong2.setIsBanned(false);
    dummySong2.setIsPublic(true);
    dummySong2.setOwner(a);
    dummySong2.setTitle("Here come dat boi");
    dummySong2.setTrackLength(120);
    dummySong2.setHasAudio(false);

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
