package webspotify.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;
import webspotify.repo.AlbumRepository;
import webspotify.repo.PlaylistRepository;
import webspotify.repo.SongRepository;
import webspotify.responses.QueueResponse;
import webspotify.responses.SongResponse;
import webspotify.types.RepeatType;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("queueService")
public class QueueService {

  @Autowired
  SongRepository songRepository;
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  AlbumRepository albumRepository;

  @Transactional
  public Response retrieveEntireQueue(SongQueue queue) {
    return ResponseUtilities.filledSuccess(new QueueResponse(queue));
  }

  @Transactional
  public Response addSongToQueue(SongQueue queue, int songId) {
    if (songRepository.exists(songId)) {
      Song songToAdd = songRepository.findOne(songId);
      queue.enqueueSong(songToAdd);
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response addPlaylistToQueue(SongQueue queue, int playlistId) {
    if (playlistRepository.exists(playlistId)) {
      Playlist playlistToAdd = playlistRepository.findOne(playlistId);
      queue.enqueueCollection(playlistToAdd.getSongs());
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response addAlbumToQueue(SongQueue queue, int albumId) {
    if (albumRepository.exists(albumId)) {
      Album albumToAdd = albumRepository.findOne(albumId);
      queue.enqueueCollection(albumToAdd.getSongs());
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response getNextSong(SongQueue queue) {
    Song songToReturn = songRepository.findOne(1);
    if (songToReturn == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.QUEUE_OUT_OF_BOUNDS);
    } else {
      songToReturn.incrementListens();
      songRepository.save(songToReturn);
      return ResponseUtilities.filledSuccess(new SongResponse(songToReturn));
    }
  }

  @Transactional
  public Response getPrevSong(SongQueue queue) {
    Song songToReturn = songRepository.findOne(1);
    if (songToReturn == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.QUEUE_OUT_OF_BOUNDS);
    } else {
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
}
