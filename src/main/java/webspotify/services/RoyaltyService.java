package webspotify.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Song;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

/**
 *
 * @author Cardinals
 */
@Service("royaltyService")
public class RoyaltyService {

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
    double pctSongOwnership = song.getTotalListens()/(totalListens*1.0);
    pctSongOwnership *= ConfigConstants.PCT_GIVEN_TO_ARTISTS;
    double totalRevenue = this.getMonthlyRevenue();
    return pctSongOwnership * totalRevenue;
  }

  public double getMonthlySongRevenue(Song song) {
    int totalMonthlyListens = songRepository.getMonthlySongListens();
    double pctSongOwnership = song.getMontlyListens()/(totalMonthlyListens*1.0);
    pctSongOwnership *= ConfigConstants.PCT_GIVEN_TO_ARTISTS;
    double totalMonthlyRevenue = this.getMonthlyRevenue();
    return pctSongOwnership * totalMonthlyRevenue;
  }

}
