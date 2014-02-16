/**
 * Facilities for developping and interacting with Athena.
 * <p>
 * Those classes are meant to be accessed either programmatically or within a command line. Different parameters may be provided when used in a command line:
 * <ul>
 * <li><code>imageName</code> Athena.image by default</li>
 * <li><code>-i</code> Interaction enabled (disabled by default), only for the shell</li>
 * <li><code>-v</code> Verbose mode (disactivated by default)</li>
 * </ul>
 * The parameter order has no effect.
 *
 * For example, the following invocations are valid:<br>
 * <code>java -cp athena-0.1.jar net.raydeejay.gossip.engine.GossipServer -v</code><br>
 * <code>java -cp athena-0.1.jar net.raydeejay.gossip.engine.GossipServer NewImage.image -v</code><br>
 * <code>java -cp athena-0.1.jar net.raydeejay.gossip.engine.AthenaGUI</code><br>
 * <code>java -cp athena-0.1.jar net.raydeejay.gossip.engine.AthenaGUI NewImage.image</code><br>
 *
 * @author Alexandre Bergel (<a href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
package net.raydeejay.gossip.engine;
