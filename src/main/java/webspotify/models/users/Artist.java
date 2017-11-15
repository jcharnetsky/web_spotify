package webspotify.models.users;

import webspotify.models.media.Concert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import webspotify.models.media.Song;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "artists")
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "ARTIST")
public class Artist extends User {

    @Column(name = "stageName")
    private String stageName;
    @Column(name = "about")
    private String about;
    @ElementCollection
    @CollectionTable(name = "artistAliases")
    private List<String> aliases;
    @ManyToMany(mappedBy = "artists")
    private Set<Concert> concerts;
    @OneToMany(mappedBy = "owner")
    private List<Song> ownedSongs;

    public Artist() {
        super();
        this.aliases = new ArrayList<String>();
        this.concerts = new HashSet<Concert>();
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        if (this.stageName != null) {
            aliases.add(this.stageName);
        }
        this.stageName = stageName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public Set<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(Set<Concert> concerts) {
        this.concerts = concerts;
    }

    public List<Song> getOwnedSongs() {
        return ownedSongs;
    }

    public void setOwnedSongs(List<Song> ownedSongs) {
        this.ownedSongs = ownedSongs;
    }

}
