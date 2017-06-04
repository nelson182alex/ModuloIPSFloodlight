package net.floodlightcontroller.tracker;



import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.TransportPort;
import org.projectfloodlight.openflow.types.VlanVid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.estatistica.Estatistica;
import net.floodlightcontroller.packet.ARP;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.persistencia.Persistencia;

public class ModuloCaptura implements IOFMessageListener, IFloodlightModule {
	
	protected IFloodlightProviderService floodlightProvider;
	protected Set<Long> macAddresses;
	protected static Logger logger;
	Persistencia p = new Persistencia();

@Override
public String getName() {
    return ModuloCaptura.class.getSimpleName();
}



@Override
public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}


@Override
public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
    Collection<Class<? extends IFloodlightService>> l =
        new ArrayList<Class<? extends IFloodlightService>>();
    l.add(IFloodlightProviderService.class);
    return l;
}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}



@Override
public void init(FloodlightModuleContext context) throws FloodlightModuleException {
    floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
    macAddresses = new ConcurrentSkipListSet<Long>();
    logger = LoggerFactory.getLogger(ModuloCaptura.class);
}

@Override
public void startUp(FloodlightModuleContext context) {
    floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
}


@Override

public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
	int i = 0;
	int cont = 1;
	
    switch (msg.getType()) {
    case PACKET_IN:
        /* Retrieve the deserialized packet in message */
        Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
       
 
        /* Various getters and setters are exposed in Ethernet */
        MacAddress srcMac = eth.getSourceMACAddress();
        VlanVid vlanId = VlanVid.ofVlan(eth.getVlanID());
 
        /* 
         * Check the ethertype of the Ethernet frame and retrieve the appropriate payload.
         * Note the shallow equality check. EthType caches and reuses instances for valid types.
         */
        if (eth.getEtherType() == EthType.IPv4) {
            /* We got an IPv4 packet; get the payload from Ethernet */
            IPv4 ipv4 = (IPv4) eth.getPayload();
             
            /* Various getters and setters are exposed in IPv4 */
            byte[] ipOptions = ipv4.getOptions();
            IPv4Address dstIp = ipv4.getDestinationAddress();
           
             
            /* 
             * Check the IP protocol version of the IPv4 packet's payload.
             * Note the deep equality check. Unlike EthType, IpProtocol does
             * not cache valid/common types; thus, all instances are unique.
             */
            if (ipv4.getProtocol().equals(IpProtocol.TCP)) {
                /* We got a TCP packet; get the payload from IPv4 */
                TCP tcp = (TCP) ipv4.getPayload();
  
                /* Various getters and setters are exposed in TCP */
                TransportPort srcPort = tcp.getSourcePort();
                TransportPort dstPort = tcp.getDestinationPort();
                
                short flags = tcp.getFlags();
                
               
                 
                /* Your logic here! */
                Estatistica est = new Estatistica();
               
                String protocolo = "TCP";
                String portaOrigem = srcPort.toString();
                String portaDestino = dstPort.toString();
                String IPOrigem = ipv4.getSourceAddress().toString();
                String IPDestino = ipv4.getDestinationAddress().toString();
                
                
                
                System.out.println("Protocolo:  " + protocolo);
                System.out.println("IP de Origem:  " + ipv4.getSourceAddress().toString());
                System.out.println("IP de Destino:  " + ipv4.getDestinationAddress().toString());
                System.out.println("Porta de Origem:  " + srcPort.getPort());
                System.out.println("Porta de Destino:  " + dstPort.getPort());
                
            
                	//Insere informação no banco e calcula Entropia a cada x número de pacotes
            	     p.insert(i, protocolo, IPOrigem, IPDestino, portaOrigem, portaDestino);
            	   //Métodos de Treinamento
                   // est.entropiaIPOrigemTreinamento(50, IPOrigem);
                	//est.entropiaIPDestinoTreinamento(50, IPDestino);
                	//est.entropiaPortaOrigemTreinamento(50, portaOrigem);
                	//est.entropiaPortaDestinoTreinamento(50, portaDestino);
                	
       
                	
                	est.entropiaIPOrigemDetectaAtaque(50, IPOrigem);
                	est.entropiaIPDestinoDetectaAtaque(50, IPDestino);
                	est.entropiaPortaOrigemDetectaAtaque(50, portaOrigem);
                	est.entropiaPortaDestinoDetectaAtaque(50, portaDestino);
                	
                	//est.detectarAtaqueGeral();
                	
               
            } else if (ipv4.getProtocol().equals(IpProtocol.UDP)) {
                /* We got a UDP packet; get the payload from IPv4 */
                UDP udp = (UDP) ipv4.getPayload();
               
  
                /* Various getters and setters are exposed in UDP */
                TransportPort srcPort = udp.getSourcePort();
                TransportPort dstPort = udp.getDestinationPort();
                 
                /* Your logic here! */
                Estatistica est = new Estatistica();
                String protocolo = "UDP";
                String portaOrigem = srcPort.toString();
                String portaDestino = dstPort.toString();
                String IPOrigem = ipv4.getSourceAddress().toString();
                String IPDestino = ipv4.getDestinationAddress().toString();
                
                System.out.println("IP de Origem:  " + ipv4.getSourceAddress().toString());
                System.out.println("IP de Destino:  " + ipv4.getDestinationAddress().toString());
                System.out.println("Porta de Origem:  " + srcPort.getPort());
                System.out.println("Porta de Destino:  " + dstPort.getPort());
                  
                	
             
              //Insere informação no banco e calcula Entropia a cada x número de pacotes
            	p.insert(i, protocolo, IPOrigem, IPDestino, portaOrigem, portaDestino);
            	//Métodos de Treinamento
                // est.entropiaIPOrigemTreinamento(50, IPOrigem);
            	 //est.entropiaIPDestinoTreinamento(50, IPDestino);
            	 //est.entropiaPortaOrigemTreinamento(50, portaOrigem);
            	 //est.entropiaPortaDestinoTreinamento(50, portaDestino);
            	
   
            	
            	est.entropiaIPOrigemDetectaAtaque(50, IPOrigem);
            	est.entropiaIPDestinoDetectaAtaque(50, IPDestino);
            	est.entropiaPortaOrigemDetectaAtaque(50, portaOrigem);
            	est.entropiaPortaDestinoDetectaAtaque(50, portaDestino);
            	
            	//est.detectarAtaqueGeral();
            	
           
              
            }
 
        } else if (eth.getEtherType() == EthType.ARP) {
        	
            /* We got an ARP packet; get the payload from Ethernet */
            ARP arp = (ARP) eth.getPayload();
 
            /* Various getters and setters are exposed in ARP */
            boolean gratuitous = arp.isGratuitous();
 
        } else {
            /* Unhandled ethertype */
        }
        System.out.println("MAC de Origem:  " + eth.getSourceMACAddress().toString());
        System.out.println("MAC de Destino:  " + eth.getDestinationMACAddress().toString());
       
        break;
    default:
        break;
    }
    return Command.CONTINUE;
}



@Override
public Collection<Class<? extends IFloodlightService>> getModuleServices() {
	// TODO Auto-generated method stub
	return null;
}


}
