package Inteca.rekrutacja.FamilyApp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    private int id;
    private String familyName;
    private int nrOfInfants;
    private int nrOfChildren;
    private int nrOfAdults;
    private List<FamilyMember> members;
}
