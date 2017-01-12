import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

interface Statement{
	public void run(HashMap hm, boolean isPrint);
	public void run(HashMap hm, int forCount, boolean isPrint);
	public void run(HashMap hm, boolean whileMode, boolean isPrint);
	public void reLoad(HashMap hm);
}

/*------------------------------------------------------------*/
class Program{
	StatementList list;

	public Program(StatementList list){
		this.list = list;
	}

	public void run(HashMap var){
		if (list == null){
			//System.out.println("nul cmnr");
			return;
		}
		list.run(var);
	}
}

class StatementList{
	HashMap local;
	ArrayList<Statement> statement;

	public StatementList(Statement s, HashMap var){
		statement = new ArrayList<Statement>();
		statement.add(s);

		this.local = var;
	}

	public void add(Statement s){
		statement.add(s);
	}
	
	/*
	public void reLoad(HashMap var){
		for(int i=0;i<statement.size();i++){
			statement.get(i).reLoad(var);
		}
	}
	*/

	public void run(HashMap var){
		for(int i=0;i<statement.size();i++){
			//statement.get(i).reLoad(var);
			statement.get(i).run(var,true);
		}
	}
	
	public void run(HashMap var, int forCount){
		for(int i=0;i<statement.size();i++){
			//statement.get(i).reLoad(var);
			statement.get(i).run(var,forCount,true);
		}
	}
	
	public void run(HashMap var, boolean whileMode){
		for(int i=0;i<statement.size();i++){
			//statement.get(i).reLoad(var);
			statement.get(i).run(var,whileMode,true);
		}
	}
}

/*---------------------------------------------*/
class AssignmentStatement implements Statement {
	Reference r = null;
	Expression e = null;
	List l = null;
	boolean isDuplicate = false;

	public AssignmentStatement(Reference r, Expression e, HashMap var) {

		
		//this.local = new HashMap<>(var);
		//local.put(r.getValue(), e.getValue());
		if (var.containsKey(r.getId()))
			isDuplicate = true;
		r.setValue(e.getValue());
		var.put(r.getId(), e.getValue());
		var.put("ans", e.getValue());
		//System.out.println("put " + r.getId() + " : " + e.getValue());
		
		this.r = r;
		this.e = e;
	}

	public AssignmentStatement(Reference r, List l, HashMap var) {	
		//this.local = new HashMap<>(var);
		//local.put(r.getValue(), l.getValue());
		if (var.containsKey(r.getId()))
			isDuplicate = true;
		r.setValue(l.getValue());
		var.put(r.getId(), l.getValue());
		var.put("ans", l.getValue());
		//System.out.println("put " + r.getId() + " : " + l.getValue());
		
		this.r = r;
		this.l = l;
	}
	
	public Reference getRef(){
		return this.r;
	}
	
	public Expression getExpr(){
		return this.e;
	}
	
	public List getList(){
		return this.l;
	}
	
	public boolean removeNotExist(HashMap var){
		if (isDuplicate){
			var.remove(r.getId());
			return true;
		}
		return false;
	}
	
	public boolean removeLocal(HashMap var){
		if (!isDuplicate){
			var.remove(r.getId());
			return true;
		}
		return false;
	}
	
	public void assign(AssignmentStatement a){
		this.r = a.r;
		this.e = a.e;
		this.l = a.l;
		this.isDuplicate = a.isDuplicate;
	}
	
	public void action(HashMap var){
		reLoad(var);
		
		AssignmentStatement tmp = null;
		if (e != null){
			e.action(var);
			tmp = new AssignmentStatement(r, e, var);
			//System.out.println("Assign action :" + e.getValue());
		}
		
		if (l != null){
			tmp = new AssignmentStatement(r, l, var);
		}
		
		this.assign(tmp);
		
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		if (e != null)
			var.put("ans", e.getValue());
		
		if (l != null)
			var.put("ans", l.getValue());
		
		if (isPrint)
			if (!var.containsKey(r.getId())){
				System.out.println("'" + r.getId() + "' undefined");
				return;
			}
			else{
				//System.out.println(r.getValue() + " = " + var.get(r.getValue()));
				System.out.println(r.getId() + " = " + r.getValue());
			}
	}

	@Override
	public void reLoad(HashMap var) {
		// TODO Auto-generated method stub
		r.reload(var);
	}

	@Override
	public void run(HashMap var, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		run(var,isPrint);
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		action(var);
		run(var, isPrint);
	}

}



/*--------------------------------------------------------*/
class Reference{
	String id;
	Object value = null; 
	
	public Reference(String id, HashMap var) {
		this.id = id;
		
		if (var.containsKey(id))
			this.value = var.get(id);
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setValue(Object value){
		this.value = value;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	public void reload(HashMap var){
		if (var.containsKey(this.id))
			this.value = var.get(id);
	}
}

/*--------------------------------------------------------*/
class List implements Statement{
	Vector<Object> value = new Vector<>();
	
	public List(Expression e1, Expression e2){
		float a = 0, b = 0;
		if (e1.getValue() instanceof Integer){
			a = ((Integer) e1.getValue()).floatValue();
		}
		else
			a = (float) e1.getValue();
		
		if (e2.getValue() instanceof Integer){
			b = ((Integer) e2.getValue()).floatValue();
		}
		else
			b = (float) e2.getValue();
		
		for (float f = a; f <= b; f = f + 1){
			//System.out.print(f + " ");
			value.add(f);
		}
	}
	
	public List(Expression e1, Expression e2, Expression e3){
		float a = 0, b = 0, c = 0;
		
		if (e1.getValue() instanceof Integer){
			a = ((Integer) e1.getValue()).floatValue();
		}
		else
			a = (float) e1.getValue();
		
		if (e2.getValue() instanceof Integer){
			b = ((Integer) e2.getValue()).floatValue();
		}
		else
			b = (float) e2.getValue();
		
		if (e3.getValue() instanceof Integer){
			c = ((Integer) e3.getValue()).floatValue();
		}
		else
			c = (float) e3.getValue();
		
		for (float f = a; f <= c; f += b ){
			value.add(f);
		}
	}
	
	public Vector<Object> getValue(){
		return value;
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		// TODO Auto-generated method stub
		for (int i = 0; i < value.size(); ++i){
			System.out.print(value.get(i) + " ");
		}
		
		System.out.println();
	}

	@Override
	public void reLoad(HashMap hm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap var, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		run(var,isPrint);
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		run(var,isPrint);
	}
}


/*--------------------------------------------------------*/
class Expression implements Statement {
	static String mul = "*";
	static String div = "/";
	static String plus = "+";
	static String minus = "-";
	static String power = "^";
	static String l_op = "<";
	static String le_op = "<=";
	static String g_op = ">";
	static String ge_op = ">=";
	static String or = "|";
	static String and = "&";
	static String eq = "==";
	static String ne = "~=";
	
	Expression E1 = null;
	Expression E2 = null;
	String OP = null;
	Reference R = null;
	
	Object value;
	boolean isPrint = false;

	
	public Expression(Expression e1 , String op, HashMap var){
		this.E1 = e1;
		this.OP = op;
		
		Object result = 0;
		
		/*Float Calculator*/
		float a = 0;
		
		////////////////////////
		if (e1.getValue() instanceof Integer){
			a = ((Integer) e1.getValue()).floatValue();
			//System.out.println("e1: " + e1.getValue());
		}
		else if (e1.getValue() instanceof Float){
			a = (float) e1.getValue();
			//System.out.println("e1: " + e1.getValue());
		}
		else if (e1.getValue() instanceof Double)
		{
			a = ((Double) e1.getValue()).floatValue();
		}
		else
		{
			System.out.println("Unknow type: " + e1.getValue());
		}
		
		if (op.equals(plus)){
			result = a; 
		}
		else if(op.equals(minus)){
			result = -a;
		}
		
		this.value = result;
		putAns(result, var);
	}
	
	public Expression (int n, HashMap var){
		this.value = n;
		putAns(n, var);
	}
	
	public Expression(float f, HashMap var){
		this.value = f;
		putAns(f, var);
	}
	
	public Expression(Expression e, HashMap var){
		this.E1 = e;
		this.value = e.getValue();
		putAns(value, var);
	}
	
	public Expression(Reference r, HashMap var){
		/*
		if (var.containsKey(r.getValue())){
			this.r = r;
			this.value = var.get(r.getValue());
		}
		else {
			this.r = null;
			this.value = null;
		}*/
		
		if (var.containsKey(r.getId())){
			this.R = r;
			this.value = r.getValue();
			//System.out.println("rrrrrr -> " + r.getValue());
		}
		else{
			this.R = r;
			this.value = null;
		}
	}
	
	public Expression(Expression e1, Expression e2, String op, HashMap var){
		this.E1 = e1;
		this.E2 = e2;
		this.OP = op;
		
		Object result = 0;	
		
		if (e1.getValue() instanceof Vector || e2.getValue() instanceof Vector){
			Object o1 = e1.getValue();
			Object o2 = e2.getValue();
			
			Operation oper = new Operation(o1, o2);
			
			if (op.equals(mul)){
				result = oper.mul();
			}
			else if (op.equals(div)){
				result = oper.div(); 
			}
			else if (op.equals(plus)){
				result = oper.plus(); 
			}
			else if (op.equals(minus)){
				result = oper.minus(); 
			}
			
			this.value = result;
			putAns(result, var);
			return;
		}
		
		/*Float Calculator*/
		float a = 0, b = 0;
		
		////////////////////////
		if (e1.getValue() instanceof Integer){
			a = ((Integer) e1.getValue()).floatValue();
			//System.out.println("e1: " + e1.getValue());
		}
		else if (e1.getValue() instanceof Float){
			a = (float) e1.getValue();
			//System.out.println("e1: " + e1.getValue());
		}
		else if (e1.getValue() instanceof Double)
		{
			a = ((Double) e1.getValue()).floatValue();
		}
		else {
			System.out.println("e1: " + e1.getValue());
		}
		
		/////////////////////////
		if (e2.getValue() instanceof Integer){
			b = ((Integer) e2.getValue()).floatValue();
			//System.out.println("e2: " + e2.getValue());
		}
		else if (e2.getValue() instanceof Float){
			b = (float) e2.getValue();
			//System.out.println("e2: " + e2.getValue());
		}
		else if (e2.getValue() instanceof Double)
		{
			b = ((Double) e2.getValue()).floatValue();
		}
		else {
			System.out.println("e2: " + e2.getValue());
		}
			
		
		/**perform operator for float**/
		if (op.equals(power)){
			double d1 = ((Float) a).doubleValue();
			double d2 = ((Float) b).doubleValue();
			result = Math.pow(d1, d2);
		}
		else if (op.equals(mul)){
			result = a * b; 
		}
		else if (op.equals(div)){
			result = a / b; 
		}
		else if (op.equals(plus)){
			result = a + b; 
		}
		else if (op.equals(minus)){
			result = a - b; 
		}
		else if (op.equals(l_op)){
			result = a < b ? 1 : 0;
		}
		else if (op.equals(le_op)){
			result = a <= b ? 1 : 0;
		}
		else if (op.equals(g_op)){
			result = a > b ? 1 : 0;
		}
		else if (op.equals(ge_op)){
			result = a >= b ? 1 : 0;
		}
		else if (op.equals(or)){
			if (a != 0 || b != 0)
				result = 1;
		}
		else if (op.equals(and)){
			if (a != 0 && b != 0)
				result = 1;
		}
		else if (op.equals(eq)){
			if (a == b )
				result = 1;
		}
		else if (op.equals(ne)){
			if (a != b)
				result = 1;
		}
		
		this.value = result;
		putAns(result, var);
	}

	public Object getValue(){
		if (value instanceof Boolean){
			if ((boolean) value)
				return 1;
			return 0;
		}
		else if (value instanceof Integer){
			//return String.valueOf(value);
			return new Integer((int) value);
		}
		else if (value instanceof Float){
			//return String.valueOf(value);
			return new Float((float) value);
		}
		else if (value instanceof Vector){
			//reLoad(var);
			return this.value;
		}
		else if (value == null)
			return null;
		else 
			return this.value;
		
		//return 0;
	}
	
	public void assign(Expression e){
		this.E1 = e.E1;
		this.E2 = e.E2;
		this.OP = e.OP;
		this.R = e.R;
		this.value = e.value;
		this.isPrint = e.isPrint;
	}
	
	public void action(HashMap var){
		reLoad(var);
		
		if (E1 != null)
			E1.action(var);
			
		if (E2 != null)
			E2.action(var);
		
		Expression tmp = null;
		if (E1 != null && E2 != null && OP != null){
			tmp = new Expression(E1,E2,OP, var);
		}
		else if (E1 != null && OP != null){
			tmp = new Expression(E1,OP,var);
		}
		else if (E1 != null && OP == null){
			tmp = new Expression(E1,var);
		}
		else if (R != null){
			R.reload(var); /* case 1 */
			//reLoad(var); /* case 2 WRONG -> use R.reload(), not this.reload() */
			tmp = new Expression(R, var);
			//System.out.println("Expression action: R " + R.getValue());
		}
		
		if (tmp != null)
			this.assign(tmp);
		//System.out.println("Expression action: " + value);
	}
	
	public void putAns(Object v, HashMap var){
		var.put("ans",v);
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		var.put("ans",getValue());

		if (isPrint){
			if (getValue() == null){
				System.out.println("Error: null value");	
				return;
			}
			
			if (this.R == null && getValue() instanceof Vector){
				Vector v = (Vector) getValue();
				for (Object i:v){
					System.out.println(i);
				}
				
				return;
			}
			
			System.out.println(getValue());
		}
	}

	@Override
	public void reLoad(HashMap var) {
		// TODO Auto-generated method stub
		if (R == null)
			return;
		
		if (var.containsKey(R.getId())){
			this.value = var.get(R.getId());
		}
		else {
			this.value = null;
		}
		
	}

	@Override
	public void run(HashMap var, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		var.put("ans",getValue());

		if (isPrint){
			if (getValue() == null){
				System.out.println("Error: null value");	
				return;
			}
			
			if (this.R == null && getValue() instanceof Vector){
				Vector v = (Vector) getValue();
				System.out.println(v.get(forCount));
				
				return;
			}
			
			System.out.println(getValue());
		}
		
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		action(var);
		run(var, isPrint);
	}
}

class SelectionStatement implements Statement{
	Object value;

	Expression e;
	StatementList sl01 = null;
	StatementList sl02 = null;
	
	public SelectionStatement(Expression e, StatementList sl01) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.sl01 = sl01;
	}
	
	public SelectionStatement(Expression e, StatementList sl01, StatementList sl02) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.sl01 = sl01;
		this.sl02 = sl02;
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		// TODO Auto-generated method stub
		float a = 0;
		if (e.getValue() instanceof Integer)
			a = ((Integer) e.getValue()).floatValue();
		else 
			a = (float) e.getValue();
		
		//System.out.println("Condtion: " + a);
		if (a != 0)
			sl01.run(var);
		else if (sl02 != null)
				sl02.run(var);
		
		
	}

	@Override
	public void reLoad(HashMap hm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap hm, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		run(var,isPrint);
	}
}

class ForStatement implements Statement{
	AssignmentStatement a;
	StatementList sl;
	HashMap local= null;	
	
	public ForStatement(AssignmentStatement a, StatementList sl, HashMap var) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.sl = sl;
		
		local = new HashMap<>(var);
		//var.put(id, l);
		//local.put(id, l);
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		// TODO Auto-generated method stub
		
		int count = 0;
		if (a.removeLocal(var)){
			//System.out.println("remove Local");
			
			for (Object i:a.getList().getValue()){
				//System.out.println("i: " + i);
				//System.out.println("Sym: " + a.getRef().getValue());
				
				//System.out.println("-->" + local.get(a.getRef().getValue()));
				//local.put(a.getRef().getValue(), i);	
				
				//int count = ((Float) i).intValue();
				sl.run(var,count);
				++count;
			}
			
		}
		else{
			for (Object i:a.getList().getValue()){
				//System.out.println("i: " + i);
				//System.out.println("Sym: " + a.getRef().getValue());
				
				//System.out.println("-->" + local.get(a.getRef().getValue()));
				//local.put(a.getRef().getValue(), i);	
				
				//int count = ((Float) i).intValue();
				sl.run(var,count);		
				++count;
			}
			
			Object o = a.getList().getValue().lastElement();		
			var.put(a.getRef().getId(), o);
		}

		
	
		
		/*
		for (Object i:a.getList().getValue()){
			//System.out.println("i: " + i);
			//System.out.println("Sym: " + a.getRef().getValue());
			System.out.println("-->" + local.get(a.getRef().getValue()));
			local.put(a.getRef().getValue(), i);	
		}
		/*
		*/
		//var.remove(id);
		//local.remove(id);
		//var.putAll(local);
	}

	@Override
	public void reLoad(HashMap hm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap hm, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		run(var,isPrint);
	}
}

/*---------------------------------------------------------------------*/
class WhileStatement implements Statement{
	Expression e = null;
	StatementList sl = null;
	HashMap local = null;
	
	public WhileStatement(Expression e, StatementList sl, HashMap var) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.sl = sl;
		
		this.local = var;
	}
	
	@Override
	public void run(HashMap var, boolean isPrint) {
		// TODO Auto-generated method stub
		int i = 0;
		if (e.getValue() instanceof Vector){
			Vector v = (Vector) e.getValue();
			i = v.size();
			
			return;

		}
		
		float f = 0;
		if (e.getValue() instanceof Float){
			f = (Float) e.getValue();
			
			//System.out.println("while: " + f);
			while (f != 0){
				sl.run(var);
			}
			return;
		}
	
		i = 0;
		int t = 0;
		if (e.getValue() instanceof Integer){
			i = (int) e.getValue();
			
			//System.out.println("while: int " + i);
			while (i != 0){
				
				sl.run(var,true);
				e.action(var);
				i = (int) e.getValue();
				
				//System.out.println(e.E1.getValue()+ " so sanh " + e.E2.getValue() );
				//System.out.println("Real i: " + i);
				++t;
			}
			return;
		}
	}

	@Override
	public void run(HashMap var, int forCount, boolean isPrint) {
		// TODO Auto-generated method stub
		int i = 0;
		if (e.getValue() instanceof Vector){
			Vector v = (Vector) e.getValue();
			i = v.size();
			
			return;

		}
		
		float f = 0;
		if (e.getValue() instanceof Vector){
			f = (Float) e.getValue();
			while (f != 0){
				sl.run(var);
			}
			
			return;
		}
	}

	@Override
	public void reLoad(HashMap var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(HashMap var, boolean whileMode, boolean isPrint) {
		// TODO Auto-generated method stub
		reLoad(var);
		run(var,isPrint);
	}
	
}



/*
class ExpressionStatement implements Statement {
	Expression e;
	boolean isPrint = false;

	public ExpressionStatement(Expression e, boolean isPrint) {
		this.e = e;
		this.isPrint = isPrint;
	}

	@Override
	public void run(HashMap hm, boolean isPrint) {
		e.execute(hm,isPrint);
	}

}
*/

/*class AssignmentExpression{
Object value;

public AssignmentExpression(Object o){
	this.value = o;	
}

public AssignmentExpression(String k, Object v,HashMap hm){
	this.value = k;
	hm.put(k,v);
}

public Object getValue(){
	if (value instanceof Boolean){
		if ((boolean) value)
			return 1;
		return 0;
	}
	else if (value instanceof Integer){
		//return String.valueOf(value);
		return new Integer((int) value);
	}
	else if (value instanceof String){
		return new String((String) value);
	}

	return 0;
}

public void execute(HashMap hm, boolean isPrint){
	hm.put("ans",getValue());

	if (isPrint)		
		if (!hm.containsKey(value)){
			System.out.println("'" + value + "' undefined");
			return;
		}
		else
			System.out.println(value + " = " + hm.get(value));	
}
}
*/