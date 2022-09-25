package Inteca.rekrutacja.FamilyMemberApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMember {
    private int id;
    private int familyId;
    private int age;
    private String familyName;
    private String givenName;
}
