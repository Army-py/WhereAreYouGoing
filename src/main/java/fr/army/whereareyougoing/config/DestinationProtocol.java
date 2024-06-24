package fr.army.whereareyougoing.config;

public record DestinationProtocol(int maxProtocolVersion, int minProtocolVersion,
                                  ProtocolVersionMessage protocolVersionMessage,
                                  ProtocolVersionTitle protocolVersionTitle) {

}
