package kuba.malewicz.password;

import kuba.malewicz.appuser.AppUser;
import kuba.malewicz.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/loggedin")
public class LoginController {

    @Autowired
   private PasswordService passwordService;
   @Autowired
    private AppUserRepository appUserRepository;

    @RequestMapping(value = "/getpasswords",method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {

        Optional<AppUser> appUser = appUserRepository.findByEmail(principal.getName());
        if(appUser.isEmpty()){
            throw new IllegalStateException("app user not found");
        }
        passwordService.addPassword(appUser.get(), new PasswordRequest("test", "test", "test" ));


        return passwordService.getPasswords(appUser.get()) + "\n";
    }


    @PostMapping(value = "/addpassword")
    public void addPassword(Principal principal, @RequestBody PasswordRequest passwordRequest){

        Optional<AppUser> appUser = appUserRepository.findByEmail(principal.getName());
        if (appUser.isEmpty()){
            throw new IllegalStateException("app user not found");
        }

        passwordService.addPassword(appUser.get(), passwordRequest);
    }

    @RequestMapping("/updatepassword")
    @PutMapping(path = "{passwordId}")
    public void updatePassword(@PathVariable("passwordId") Long id, @RequestBody PasswordRequest passwordRequest, Principal principal){

        Optional<AppUser> appUser = appUserRepository.findByEmail(principal.getName());

        if (appUser.isEmpty()){
            throw new IllegalStateException("app user not found");
        }

        passwordService.updatePassword(id, passwordRequest, appUser.get());

    }

    @RequestMapping("/deletepassword")
    @DeleteMapping( path = "{passwordId}")
    public void deletePassword(@PathVariable("passwordId") Long id){
        passwordService.deletePassword(id);
    }







}
