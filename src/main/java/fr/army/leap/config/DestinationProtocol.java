package fr.army.leap.config;

public record DestinationProtocol(int maxProtocolVersion, int minProtocolVersion,
                                  ProtocolVersionMessage protocolVersionMessage,
                                  ProtocolVersionTitle protocolVersionTitle) {

}
