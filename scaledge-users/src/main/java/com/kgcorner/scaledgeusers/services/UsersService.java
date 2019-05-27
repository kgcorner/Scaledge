package com.kgcorner.scaledgeusers.services;

/*
Description : <Write is class Description>
Author: kumar
Created on : 27/5/19
*/

import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledgeusers.dao.entity.User;
import com.kgcorner.scaledgeusers.dao.repo.UsersDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersDataRepo usersDataRepo;

    /**
     * Returns user with given id
     * @param id
     * @return
     */
    public User getUser(String id) {
        return usersDataRepo.getById(id, User.class);
    }

    /**
     * Register given user if it is valid
     * @param user
     * @return
     */
    public User registerUser(User user) {
        if(!user.isValid()) {
            throw new IllegalArgumentException("User is not valid");
        }
        return usersDataRepo.create(user);
    }

    /**
     * Add followerId into follower of following id
     * @param followerId
     * @param followingId
     */
    public void followUser(String followerId, String followingId) {
        User follower = usersDataRepo.getById(followerId, User.class);
        User following = usersDataRepo.getById(followingId, User.class);
        UserPreview followerPreview = new UserPreview(follower.getId(), follower.getName());
        UserPreview followingPreview = new UserPreview(following.getId(), following.getName());
        follower.getFollowings().add(followingPreview);
        following.getFollowers().add(followerPreview);
        usersDataRepo.update(follower);
        usersDataRepo.update(following);
    }
}