package webspotify.controllers.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class BrowseController {

  /**
   * Get the JSON containing the focus and charts for the spotify overview page.
   * @return JSON containing the focus and charts
   */
  @RequestMapping(value = "/overview", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getOverviewData() {
    String[][] focusData = {{"images/rhiannon-giddens-tomorrow-is-my-turn.jpg", "Tomorrow Is My Turn", "Tomorrow Is My Turn by Rhiannon Giddens"},
            {"images/tile2.png", "dancePOP", "The guilty pleasures of dance and electronic. Cover: Eden Price and Cassie"},
            {"images/tile3.png", "Deep Focus", "Keep calm and focus. This playlist has some great, atmospheric rock to help you relax and concentrate."},
            {"images/tile4.png", "Lush Vibes", "Lo-fi and chill instrumental hip hop. Related playlist: Jazz Vibes"},
            {"images/tile5.png", "The Pulse of Americana", "Keep your finger on the pulse of Americana. We\"re featuring artists performing at AMERICANAFEST."},
            {"images/tile6.png", "Young & Free", "Live your life... this is Young & Free"}};
    String[][] chartData = {{"images/charts.png", "Global and Regional Top Charts"},
            {"images/new_releases.png", "Macklemore, The Killers, Illenium"},
            {"images/discover.png", "Recommended For You"}};

    JSONObject overview = new JSONObject();
    JSONArray focuses = new JSONArray();
    JSONArray charts = new JSONArray();
    for (int i = 0; i < 6; i++) {
      JSONObject focus = new JSONObject();
      focus.put("img", focusData[i][0]);
      focus.put("head", focusData[i][1]);
      focus.put("desc", focusData[i][2]);
      focuses.put(focus);
    }
    for (int i = 0; i < 3; i++) {
      JSONObject chart = new JSONObject();
      chart.put("img", chartData[i][0]);
      chart.put("desc", chartData[i][1]);
      charts.put(chart);
    }
    JSONObject content = new JSONObject();
    content.put("focus", focuses);
    content.put("charts", charts);
    overview.put("content", content);
    return overview.toString();
  }

  /**
   * Get the JSON containing the top charts for the spotify charts pages
   * @return JSON containing the charts
   */
  @RequestMapping(value = "/charts", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getChartsData() {
    return "";
  }

  /**
   * Get the JSON containing the genres and moods for the spotify genres and moods page.
   * @return JSON containing the Genres and Moods
   */
  @RequestMapping(value = "/genres_moods", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getGenresMoodsData() {
    return "";
  }

  /**
   * Get the JSON containing the new albums for the spotify new releases page.
   * @return JSON containing the new albums
   */
  @RequestMapping(value = "/new_releases", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getNewReleasesData() {
    return "";
  }

  /**
   * Get the JSON containing albums and artists related to the logged in user for the discover page.
   * @return JSON containing the discover data
   */
  @RequestMapping(value = "/discover", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getDiscoverData(HttpSession session) {
    return "";
  }

  /**
   * Get the JSON containing the concert information for the concerts page
   * @return JSON containing the concerts
   */
  @RequestMapping(value = "/concerts", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getConcertsData() {
    return "";
  }
}
