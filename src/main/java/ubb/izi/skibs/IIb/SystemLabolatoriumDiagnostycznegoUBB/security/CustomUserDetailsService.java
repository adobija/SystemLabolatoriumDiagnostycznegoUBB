package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Uzytkownik;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.UzytkownikRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UzytkownikRepo uzytkownikRepo;

    public CustomUserDetailsService(UzytkownikRepo uzytkownikRepo) {
        this.uzytkownikRepo = uzytkownikRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(uzytkownik.getEmail())
                .password(uzytkownik.getHasloHash())
                .roles(uzytkownik.getRola().getKod())
                .build();
    }
}
