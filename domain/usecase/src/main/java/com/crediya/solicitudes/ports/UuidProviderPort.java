package com.crediya.solicitudes.ports;

import java.util.UUID;

public interface UuidProviderPort {
    UUID generate();
    byte[] toBytes(UUID uuid);
    UUID fromBytes(byte[] bytes);
    byte[] fromString(String uuidString);
    String toString(byte[] bytes);
}
