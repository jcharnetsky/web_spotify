package webspotify.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.administration.Report;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ReportRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.types.ReportTypes;
import webspotify.types.SpotifyObjectEnum;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

/**
 *
 * @author Cardinals
 */
@Service("royaltyService")
public class RoyaltyService {

  @Autowired
  ReportRepository reportRepository;
  @Autowired
  ArtistRepository artistRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  SongRepository songRepository;

  public double getMonthlyRevenue() {
    Integer premiumUserCount = userRepository.countByIsPremiumIsTrue();
    return premiumUserCount * ConfigConstants.MONTHLY_SUBSCRIPTION_COST;
  }

  public double getTotalSongRevenue(Song song) {
    int totalListens = songRepository.getTotalSongListens();
    double pctSongOwnership = song.getTotalListens() / (totalListens * 1.0);
    pctSongOwnership *= ConfigConstants.PCT_GIVEN_TO_ARTISTS;
    double totalRevenue = this.getMonthlyRevenue();
    return pctSongOwnership * totalRevenue;
  }

  public double getMonthlySongRevenue(Song song) {
    int totalMonthlyListens = songRepository.getMonthlySongListens();
    double pctSongOwnership = song.getMontlyListens() / (totalMonthlyListens * 1.0);
    pctSongOwnership *= ConfigConstants.PCT_GIVEN_TO_ARTISTS;
    double totalMonthlyRevenue = this.getMonthlyRevenue();
    return pctSongOwnership * totalMonthlyRevenue;
  }

  @Transactional
  public Response endOfMonthRoyalty(User user) {
    List<Artist> artists = artistRepository.findAll();
    for (Artist artist : artists) {
      String email = artist.getEmail();
      String format = "Song %-24s Made $%.02f\n";
      String build = "";
      for (Song song : artist.getOwnedSongs()) {
        if (song.getMontlyListens() > 0) {
          build += String.format(format, song.getTitle(), getMonthlySongRevenue(song));
          song.setMonthlyListeners(0);
          songRepository.save(song);
        }
      }
      if (!build.isEmpty()) {
        Report createdReport = new Report();
        createdReport.setSubject("Check Payout for Artist: " + artist.getName());
        createdReport.setDescription(build);
        createdReport.setReportType(ReportTypes.CHECK);
        createdReport.setEntityType(SpotifyObjectEnum.USER);
        createdReport.setEntityId(artist.getId());
        createdReport.setCompleted(Boolean.FALSE);
        createdReport.setCreator(userRepository.findOne(user.getId()));
        reportRepository.save(createdReport);

      }
    }
    return ResponseUtilities.emptySuccess();
  }

}
