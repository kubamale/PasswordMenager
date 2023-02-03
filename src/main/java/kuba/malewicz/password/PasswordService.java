package kuba.malewicz.password;

import kuba.malewicz.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordService{

    private final PasswordRepository passwordRepository;

    public String getPasswords(AppUser appUser){

       List<Password> password = passwordRepository.findAllByAppUser(appUser);

        if (password.isEmpty()){
            throw new FindException("This user dosent have password");
        }


        return password.toString();
    }

    public void addPassword(AppUser appUser, PasswordRequest request){

        List<Password> passwords = passwordRepository.findAllByAppUser(appUser);

        for (Password p : passwords){
           if(p.getLogin().equals(request.getLogin()) && p.getPage().equals(request.getPage())){
               throw new IllegalStateException("You already have password for this page with that login");
           }
        }

        passwordRepository.save(new Password(request.getPage(), request.getLogin(), request.getPassword(),appUser ));
    }

    public void updatePassword(Long passwordId, PasswordRequest passwordRequest, AppUser appUser){
        Optional<Password> password = passwordRepository.findById(passwordId);

        if (password.isEmpty()){
            throw new IllegalStateException("Password with id: \"" + passwordId + "\" dose not exist");
        }

        List<Password> passwords = passwordRepository.findAllByAppUser(appUser);


        if(passwordRequest.getPage() == null || passwordRequest.getLogin() == null || passwordRequest.getPassword() != null){
            throw new IllegalStateException("Please enter all fields");
        }

        for (Password p : passwords){
            if (!p.getId().equals(passwordId) && p.getLogin().equals(passwordRequest.getLogin()) && p.getPage().equals(passwordRequest.getPage())){
                throw new IllegalStateException("You already have password for this page with that login");
            }
        }

        password.get().setPage(passwordRequest.getPage());
        password.get().setLogin(passwordRequest.getLogin());
        password.get().setPassword(passwordRequest.getPassword());
        passwordRepository.save(password.get());

    }

    public void deletePassword(Long passwordId){

        boolean passwordExists = passwordRepository.existsById(passwordId);

        if (!passwordExists){
            throw new IllegalStateException("password with id " + passwordId + "dose not exist");
        }


        passwordRepository.deleteById(passwordId);

    }

}
