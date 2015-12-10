package uk.co.harcourtprogramming.internetrelaycats;

import java.util.List;

/**
 * @todo Class Documentation @todo notes on how functions behave befoer
 * connection is established
 */
public interface RelayCat
{
	/**
	 * <p>Send a message to a user or channel</p>
	 * <p>Multi-line messages are sent as a series of individual lines, and are
	 * limited by the inbuilt flood control.
	 * A many-line message will block other messages from being sent by the
	 * {@link RelayCat} whilst it is being sent.</p>
	 * <p>Examples:
	 * <pre>  message("bob", "Hello!");
	 *   message("#irc", "How do I turn this thing on?");
	 * </pre></p>
	 *
	 * @param target The target of the message
	 * @param message The message to send
	 * @throws IllegalArgumentException if the target is not a valid irc
	 * nickname
	 */
	public void message(String target, String message);

	/**
	 * <p>Send an action to a user or channel</p>
	 * <p>Unlike {@link #message(java.lang.String, java.lang.String) message},
	 * only the first line of the message will be sent.
	 * This method will wait for any in progress multi-line messages.</p>
	 * <p>Examples:
	 * <pre>  act("bob", "hugs");
	 *   message("#irc", "uses an action.");
	 * </pre></p>
	 *
	 * @param target The target of the message
	 * @param message The message to send
	 * @throws IllegalArgumentException if the target is not a valid irc
	 * nickname
	 */
	public void act(String target, String message);

	/**
	 * <p>Join or create a channel</p>
	 * <p>Note: this is global for all services on the bot.</p>
	 *
	 * @param channel the channel to join (e.g. '#irc')
	 * @see #leave(java.lang.String) leave(channel)
	 * @see #channels() channels()
	 */
	public void join(String channel);

	public void setTopic(String channel, String topic);

	/**
	 * <p>Leave a channel</p>
	 * <p>Note: this is global for all services.
	 * It is generally more appropriate to locally ignore messages from a
	 * channel, as other services may want them.</p>
	 *
	 * @param channel the channel to leave (e.g. '#irc')
	 * @see #join(java.lang.String) join(channel)
	 * @see #channels() channels()
	 */
	public void leave(String channel);

	/**
	 * @return the nick of this {@link RelayCat} instance
	 */
	public String getNick();

	/**
	 * <p>Returns an array of all users in the specified channel.</p>
	 * <p>There are some important things to note about this method:-</p>
	 * <ul>
	 * <li>This method may not return a full list of users if you call it
	 * before the complete nick list has arrived from the IRC server.</li>
	 * <li>If you wish to find out which users are in a channel as soon as you
	 * join it, then you
	 * should override the onUserList method instead of calling this method, as
	 * the onUserList method is only called as soon as the full user list has
	 * been received. </li> <li>This method will return immediately, as it does
	 * <li>The bot must be in a channel to be able to know which users are in
	 * it.</li>
	 * </ul>
	 *
	 * @param channel The name of the channel to list.
	 * @return An array of User objects. This array is empty if we are not in
	 * the channel.
	 */
	public String[] names(String channel);

	/**
	 * <p>Returns an array of all channels that we are in.</p>
	 * <p>Note that if you call this method immediately after joining a new
	 * channel, the new channel may not appear in this array as it is not
	 * possible to tell if the join was successful until a response is received
	 * from the IRC server.</p>
	 *
	 * @return list of channels we are connected to
	 */
	public String[] channels();

	public boolean isConnected();

	/**
	 * <p>Gets a list of the {@link Service Services} attached to this RelayCat
	 * that match a given class.</p>
	 *
	 * @param clazz The base class to search for
	 * @return A list of services that match that class
	 */
	public <Clazz extends Service> List<Clazz> getServicesByClass(
		Class<Clazz> clazz);
}
