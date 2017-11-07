package web_spotify.controllers;

import Utils.JsonUtils;
import org.json.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BrowseController {

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getOverviewData(){return "";}

    @RequestMapping(value="/charts", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getChartsData(){return "";}

    @RequestMapping(value="/genres_moods", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getGenresMoodsData(){return "";}

    @RequestMapping(value="/new_releases", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNewReleasesData(){return "";}

    @RequestMapping(value="/discover", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDiscoverData(HttpSession session){return "";}

    @RequestMapping(value="/concerts", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getConcertsData(){return "";}

}
