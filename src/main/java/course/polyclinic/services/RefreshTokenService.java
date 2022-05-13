package course.polyclinic.services;

import course.polyclinic.components.RefreshToken;
import course.polyclinic.repo.RefreshTokenRepository;
import course.polyclinic.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @Autowired
    private UserRepo userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

//    public RefreshToken createRefreshToken(Long userId) {
//
//        RefreshToken refreshToken = new RefreshToken();
//
//        refreshToken.setUser(userRepository.findById(userId).get());
//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(UUID.randomUUID().toString());
//
//        refreshToken = refreshTokenRepo.save(refreshToken);
//        return refreshToken;
//    }


    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepo.deleteByUser(userRepository.findById(userId).get());
    }
}
