package ZCW.ChatApp.security;

import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.models.UserDTO;
import ZCW.ChatApp.repositories.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDaoRepository userDaoRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DAOUser> user = userDaoRepository.findByUserName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(),
                new ArrayList<>());
    }

    public DAOUser save(UserDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUserName(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDaoRepository.save(newUser);
    }

}
