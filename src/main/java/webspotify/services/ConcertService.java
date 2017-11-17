package webspotify.services;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Concert;
import webspotify.models.users.Artist;
import webspotify.posts.ConcertArtistChangeRequest;
import webspotify.repo.ConcertRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.ConcertResponse;

@Service("concertService")
public class ConcertService {
  @Autowired 
  ConcertRepository concertRepository;
  @Autowired
  UserRepository userRepository;
  
  @Transactional
  public Response getConcerts() {
    List<ConcertResponse> responseList = new ArrayList<ConcertResponse>();
    for (Concert concert : concertRepository.findAll()) {
      responseList.add(new ConcertResponse(concert));
    }
    return ResponseUtilities.filledSuccess(responseList);
  }
  
  @Transactional
  public Response getConcert(final int concertId) {
    Concert concertToFind = concertRepository.findOne(concertId);
    if (concertToFind == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.CONCERT_NO_EXIST);
    } 
    else {
      List<Concert> contentBody = new ArrayList<Concert>();
      contentBody.add(concertToFind);
      return ResponseUtilities.filledSuccess(contentBody);
    }
  }
  
  @Transactional
  public Response addArtistToConcert(ConcertArtistChangeRequest change) {
    Concert concert = concertRepository.findOne(change.getConcertID());
    Artist artist = (Artist) userRepository.findOne(change.getArtistID());
    if (concert == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.CONCERT_NO_EXIST);
    }
    if (artist == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.ARTIST_NO_EXIST);
    }
    if (!(concert.getArtists().contains(artist))) {
      concert.getArtists().add(artist);
      concertRepository.saveAndFlush(concert);
      return ResponseUtilities.emptySuccess();
    } 
    else {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
    }
  }
  
  @Transactional
  public Response removeArtistFromConcert(ConcertArtistChangeRequest change) {
    Concert concert = concertRepository.findOne(change.getConcertID());
    Artist artist = (Artist) userRepository.findOne(change.getArtistID());
    if (concert == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.CONCERT_NO_EXIST);
    }
    if (artist == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.ARTIST_NO_EXIST);
    }
    if (concert.getArtists().contains(artist)) {
      concert.getArtists().remove(artist);
      concertRepository.saveAndFlush(concert);
      return ResponseUtilities.emptySuccess();
    } 
    else {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
    }
  }
  
  @Transactional
  public Response removeConcert(Concert concert) {
    Concert c = concertRepository.findOne(concert.getId());
    if(c == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
    }
    concertRepository.delete(c);
    return ResponseUtilities.emptySuccess();
  }
  
  @Transactional
  public Response postConcert(Concert concert) {
    concert.setId(null);
    concert.setIsPublic(true);
    concert.setIsBanned(false);
    concertRepository.saveAndFlush(concert);
    return ResponseUtilities.emptySuccess();
  }
}

