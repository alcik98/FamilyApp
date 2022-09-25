package Inteca.rekrutacja.FamilyApp;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/families")
public class FamilyController {


    @Autowired
    FamilyRepository familyRepository;

    @GetMapping("/test")
    public String test() {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    @GetMapping ("/")
    public List<Family> getAll() {
        return familyRepository.getAll();
    }

    @GetMapping ("/getFamily/{id}")
    public Family getFamily(@PathVariable("id") int id) throws IOException, JSONException {
        return familyRepository.getFamily(id);
    }

    @PostMapping ("/createFamily")
    public String createFamily(@RequestBody Family family) {
        try {
            return familyRepository.createFamily(family);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return new RedirectView("http://localhost:8080/families/");
        return "Blad";
    }

}
