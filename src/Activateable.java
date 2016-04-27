public interface Activateable 
{
	//Note: active objects can "have" other inactive objects, such as an active player
	//"having" an inactive menu, but an inactive object should not "have" any active objects.
	public void activate();
	public void deActivate();
	public boolean isActive();
}
