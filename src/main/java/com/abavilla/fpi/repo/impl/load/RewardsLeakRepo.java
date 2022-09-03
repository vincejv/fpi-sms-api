package com.abavilla.fpi.repo.impl.load;

import com.abavilla.fpi.entity.impl.gl.GLRewardsCallback;
import com.abavilla.fpi.repo.AbsMongoRepo;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RewardsLeakRepo extends AbsMongoRepo<GLRewardsCallback> {
}
