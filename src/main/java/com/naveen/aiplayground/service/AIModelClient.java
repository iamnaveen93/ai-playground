package com.naveen.aiplayground.service;

import java.util.Optional;

public interface AIModelClient {

    Optional<String> askClient(final String message);
}
