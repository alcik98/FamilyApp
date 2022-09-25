package Inteca.rekrutacja.FamilyMemberApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequestMapping("/familyMembers")
@RestController
public class FamilyMemberController {

    @Autowired
    FamilyMemberRepository familyMemberRepository;

    @GetMapping("/test")
    public String test() {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    @GetMapping("/")
    public List<FamilyMember> getAll() {
        return familyMemberRepository.getAll();
    }

    @GetMapping ("/getFamily/{id}")
    public List<FamilyMember> getFamily(@PathVariable("id") int familyid) {
        return familyMemberRepository.getFamily(familyid);
    }

    @GetMapping ("/getMember/{id}")
    public FamilyMember getMember(@PathVariable("id") int id) {
        return familyMemberRepository.getMember(id);
    }

    @PostMapping("/createFamilyMembers")
    public int createFamilyMembers(@RequestBody FamilyMember member) {
        System.out.println(member);
        return familyMemberRepository.createFamilyMember(member);
    }

}
