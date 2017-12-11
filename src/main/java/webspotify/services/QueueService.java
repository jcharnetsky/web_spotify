package webspotify.services;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;
import webspotify.repo.AlbumRepository;
import webspotify.repo.PlaylistRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.QueueResponse;
import webspotify.responses.SongResponse;
import webspotify.types.RepeatType;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("queueService")
public class QueueService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  SongRepository songRepository;
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  AlbumRepository albumRepository;

  @Transactional
  public Response retrieveEntireQueue(User user, SongQueue queue) {
    User togetQueue = userRepository.findOne(user.getId());
    return ResponseUtilities.filledSuccess(new QueueResponse(togetQueue, queue));
  }
  
  @Transactional
  public Response clearQueue(SongQueue queue) {
    queue.clearQueue();
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response removeSongFromQueue(SongQueue queue, int songId){
    if(songRepository.exists(songId)){
      Song toRemove = songRepository.findOne(songId);
      if (queue.removeSong(toRemove)){
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.NOT_IN_QUEUE);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response addSongToQueue(SongQueue queue, int songId) {
    if (songRepository.exists(songId)) {
      Song songToAdd = songRepository.findOne(songId);
      if(songToAdd.getHasAudio()) {
        queue.enqueueSong(songToAdd);
        return ResponseUtilities.emptySuccess();
      }
      return ResponseUtilities.filledFailure(ConfigConstants.NO_AUDIO_EXIST);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }


  @Transactional
  public Response addPlaylistToQueue(SongQueue queue, int playlistId) {
    if (playlistRepository.exists(playlistId)) {
      Playlist playlistToAdd = playlistRepository.findOne(playlistId);
      ArrayList<Song> allSongs = new ArrayList<Song>(playlistToAdd.getSongs());
      queue.enqueueCollection(determineValidSongs(allSongs));
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response addAlbumToQueue(SongQueue queue, int albumId) {
    if (albumRepository.exists(albumId)) {
      Album albumToAdd = albumRepository.findOne(albumId);
      ArrayList<Song> allSongs = new ArrayList<Song>();
      allSongs.addAll(albumToAdd.getSongsInAlbum());
      queue.enqueueCollection(determineValidSongs(allSongs));
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }
  
  @Transactional
  public Response addPartialPlaylistToQueue(SongQueue queue, int playlistId, int songId) {
    if (playlistRepository.exists(playlistId) && songRepository.exists(songId)) {
      Playlist playlistToAdd = playlistRepository.findOne(playlistId);
      ArrayList<Song> allSongs = new ArrayList<Song>(playlistToAdd.getSongs());
      while(songId != allSongs.get(0).getId()) {
        allSongs.remove(0);
      }
      queue.enqueueCollection(determineValidSongs(allSongs));
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }
  
  @Transactional
  public Response addPartialAlbumToQueue(SongQueue queue, int albumId, int songId) {
    if (albumRepository.exists(albumId) && songRepository.exists(songId)) {
      Album albumToAdd = albumRepository.findOne(albumId);
      ArrayList<Song> allSongs = new ArrayList<Song>();
      allSongs.addAll(albumToAdd.getSongsInAlbum());
      while(songId != allSongs.get(0).getId()) {
        allSongs.remove(0);
      }
      queue.enqueueCollection(determineValidSongs(allSongs));
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }  
  
  @Transactional
  public Response getNextSong(SongQueue queue) {
    Song nextSong = queue.next();
    if (nextSong == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.QUEUE_OUT_OF_BOUNDS);
    } else {
      Song songToReturn = songRepository.findOne(nextSong.getId());
      songToReturn.incrementListens();
      songRepository.save(songToReturn);
      return ResponseUtilities.filledSuccess(new SongResponse(songToReturn));
    }
  }

  @Transactional
  public Response getPrevSong(SongQueue queue) {
    Song prevSong = queue.prev();
    if (prevSong == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.QUEUE_OUT_OF_BOUNDS);
    } else {
      Song songToReturn = songRepository.findOne(prevSong.getId());
      songToReturn.incrementListens();
      songRepository.save(songToReturn);
      return ResponseUtilities.filledSuccess(new SongResponse(songToReturn));
    }
  }

  @Transactional
  public Response setRepeatCurrent(SongQueue queue) {
    queue.setRepeatType(RepeatType.CURRENT);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response setRepeatLibrary(SongQueue queue) {
    queue.setRepeatType(RepeatType.LIBRARY);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response setRepeatNone(SongQueue queue) {
    queue.setRepeatType(RepeatType.NONE);
    return ResponseUtilities.emptySuccess();
  }

  public ArrayList<Song> determineValidSongs(ArrayList<Song> allSongs) {
    ArrayList<Song> validSongs = new ArrayList<Song>();
    for(int i = 0; i < allSongs.size(); i++) {
      if(allSongs.get(i).getHasAudio()) {
        validSongs.add(allSongs.get(i));
      }
    }
    return validSongs;
  }
/*
  @Transactional
  public Response addSongToHistory(SongQueue queue, int songId) {
    if (songRepository.exists(songId)) {
      Song songToAdd = songRepository.findOne(songId);
      if(songToAdd.getHasAudio()) {
        queue.pushSongHistory(songToAdd);
        return ResponseUtilities.filledSuccess(songToAdd);
      }
      return ResponseUtilities.filledFailure(ConfigConstants.NO_AUDIO_EXIST);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }
  
  @Transactional
  public Response addSavedSongsToQueue(User user, SongQueue queue) {
    User userToChange = userRepository.findOne(user.getId());
    ArrayList<Song> allSongs = new ArrayList<Song>(userToChange.getSavedSongs());
    queue.enqueueCollection(determineValidSongs(allSongs));
    return ResponseUtilities.emptySuccess();
  }
*/
}