package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(CredentialForm credentialForm){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        credentialForm.setKey(encodedKey);
        credentialForm.setPassword(encryptedPassword);
        return credentialMapper.insert(new Credentials(credentialForm.getUsername(),credentialForm.getUrl(), credentialForm.getKey(),
                credentialForm.getPassword(), credentialForm.getUserid()));
    }

    public List<Credentials> getCredential(Integer userid){
        return credentialMapper.getUserCredentials(userid);

    }

    public void updateCredential(CredentialForm credentialForm){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        credentialForm.setKey(encodedKey);
        credentialForm.setPassword(encryptedPassword);
        credentialMapper.update(credentialForm.getUsername(), credentialForm.getPassword(), credentialForm.getKey(),  credentialForm.getUrl(), credentialForm.getCredentialid(), credentialForm.getUserid());
    }

    public void deleteCredential(Integer credid, Integer userid){
        credentialMapper.delete(credid, userid);
    };


}
