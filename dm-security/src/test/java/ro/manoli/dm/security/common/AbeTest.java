package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.net.URL;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * 
 * @author Mihail
 *
 */
public class AbeTest {

	private PublicKey publicKey;
	private MasterKey masterKey;
	
	private String[] attributes;
	private BigInteger p;
	private Element g;
	private Element message;
	
	private AccessTree accessTree;
	
	@Before
	public void init() {
		publicKey = new PublicKey();
		Random rnd = new Random();
		p = BigInteger.probablePrime(256, rnd);
		URL propertiesFileURL = Abe.class.getResource("a.properties");
		Pairing e = PairingFactory.getPairing(propertiesFileURL.getPath());
		g = e.getG1().newElement(); 
	
		message = e.getGT().newElement();
		masterKey = new MasterKey();
		
		attributes = new String[] {"1", "2", "3", "4", "5"};
		
		createAccessTree();
	}
	
	private void createAccessTree() {
		AccessTreeNode n1 = new AccessTreeNode("1");
		AccessTreeNode n2 = new AccessTreeNode("2");
		AccessTreeNode n3 = new AccessTreeNode("3");
		AccessTreeNode n4 = new AccessTreeNode("4");
		AccessTreeNode n5 = new AccessTreeNode("5");
		
		AccessTreeNode r1 = new AccessTreeNode(2, n1, n2, n3);
		AccessTreeNode r2 = new AccessTreeNode(1, n4, n5);

		AccessTreeNode root = new AccessTreeNode(2, r1, r2);
		
		accessTree = new AccessTree(root);
	}

	@Test
	public void testSetupBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
	}
	
	@Test
	public void testEncryptionBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Abe.enc(publicKey, message, attributes);
	}
	
	@Test
	public void testKeyGenerationBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Abe.enc(publicKey, message, attributes);
		Abe.keygen(accessTree, masterKey);
	}
}