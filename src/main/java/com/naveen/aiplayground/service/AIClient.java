package com.naveen.aiplayground.service;

import org.springframework.stereotype.Service;

@Service
public interface AIClient {

    String askClient(final String message);
}
