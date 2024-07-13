package com.oasis.ocrspring.converter;

import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdToUserConverter implements Converter<ObjectId, User> {

    private final UserRepository userRepository;

    @Autowired
    public ObjectIdToUserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User convert(ObjectId source) {
        // Implement your logic to convert ObjectId to User
        // For example, you can fetch the User from MongoDB using the ObjectId
        return userRepository.findById(source.toHexString()).orElse(null);
    }
}
