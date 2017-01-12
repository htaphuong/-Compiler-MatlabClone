import java.util.Vector;

class Operation{
	Object o1;
	Object o2;
	
	public Operation(Object o1, Object o2) {
		// TODO Auto-generated constructor stub
		this.o1 = o1;
		this.o2 = o2;
	}	
	
	public Object plus(){
		Vector result = new Vector<>();
		
		if (o1 instanceof Vector){
			Vector v = (Vector) o1;
			Float t2 = ((Float) o2).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 + t2);
			}
		}
		else if (o2 instanceof Vector){
			Vector v = (Vector) o2;
			Float t2 = ((Float) o1).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 + t2);
			}
		}
		
		return result;
	}
	
	public Object minus(){
		Vector result = new Vector<>();
		
		if (o1 instanceof Vector){
			Vector v = (Vector) o1;
			Float t2 = ((Float) o2).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 - t2);
			}
		}
		else if (o2 instanceof Vector){
			Vector v = (Vector) o2;
			Float t2 = ((Float) o1).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t2 - t1);
			}
		}
		
		return result;
	}
	
	public Object mul(){
		Vector result = new Vector<>();
		
		if (o1 instanceof Vector){
			Vector v = (Vector) o1;
			Float t2 = ((Float) o2).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 * t2);
			}
		}
		else if (o2 instanceof Vector){
			Vector v = (Vector) o2;
			Float t2 = ((Float) o1).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 * t2);
			}
		}
		
		return result;
	}
	
	public Object div(){
		Vector result = new Vector<>();
		
		if (o1 instanceof Vector){
			Vector v = (Vector) o1;
			Float t2 = ((Float) o2).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t1 / t2);
			}
		}
		else if (o2 instanceof Vector){
			Vector v = (Vector) o2;
			Float t2 = ((Float) o1).floatValue();
			for (int i = 0; i < v.size() ; ++i){
				float t1 = ((Float) v.get(i)).floatValue();
				result.add(t2 / t1);
			}
		}
		
		return result;
	}
}