import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;


public class AstarSearchAlgo{

        public static List <TreeNode> TreeAStar = new ArrayList <TreeNode>();
        public static ArrayList<String> Values1= new ArrayList <String>();
        public static ArrayList<String> Values2=new ArrayList <String>();
        public static ArrayList<String> Values3=new ArrayList<String>();
        
        public static void main(String[] args){
     
           try{ 
              
          
          FileInputStream fstream = new FileInputStream("heuristic1.txt");
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          TreeNode node = null;
          
          while ((strLine = br.readLine()) != null)   {
          String[] tokens = strLine.split(" ");
          node = new TreeNode(tokens[0]);
          node.heuristic = Integer.parseInt(tokens[1]);
          TreeAStar.add(node);
          
          }
         in.close();   
       }
       
       catch (Exception e){
           System.err.println("Error: "+ e.getMessage());
       }

       try{

          FileInputStream fstream = new FileInputStream("tree1.txt");
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          TreeNode node = null;
         
          while ((strLine = br.readLine()) != null)   {
          String[] tokens = strLine.split(" ");
 
          for(TreeNode currentNode : TreeAStar){
             
          if(currentNode.letter.equals(tokens[0]))
             currentNode.addChildren((tokens[1]));
          
          if(currentNode.letter.equals(tokens[1])){
              currentNode.pathCostFromParent=Integer.parseInt(tokens[2]);
              currentNode.parent=tokens[0];
          }
          }

   }
          
   in.close();
   
   }catch (Exception e){
     System.err.println("Error: " + e.getMessage());
   } 
       
         
  
         for (TreeNode currentNode: TreeAStar){

         System.out.println("Node: "+ currentNode.letter +"\n"
                            +"Heuristic: " +currentNode.heuristic +"\n" +
                            "Parent: " +currentNode.parent +"\n"+
                            "path Cost from Parent: " +currentNode.pathCostFromParent+"\n"+
                            "Childrens: "+currentNode.childrens +"\n-----------");

     }

       
         List <Node> nodesMap = new ArrayList<Node>();
         
         for(TreeNode currentNode: TreeAStar){
         
             nodesMap.add(new Node(currentNode.letter, currentNode.heuristic));
        
         }
         
           try{

          FileInputStream fstream = new FileInputStream("tree1.txt");
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          
         
          while ((strLine = br.readLine()) != null)   {
          String[] tokens = strLine.split(" ");
 
          for(Node currentNode:nodesMap){
              
             if(currentNode.value.equals(tokens[0])){
                 
                 Values1.add(tokens[0]);
                 Values2.add(tokens[1]);
                 Values3.add(tokens[2]);
                 
                 
             }
          }
              
          
             
          }
   
   in.close();
   
   }catch (Exception e){
     System.err.println("Error: " + e.getMessage());
   } 
           
           List <struct> structs = new ArrayList <struct>();
            for(int i=0;i<Values1.size();i++){
                
           for(int x=0; x<nodesMap.size();x++){
          
               
               if(nodesMap.get(x).value.equals(Values1.get(i)))
                   structs.add(new struct(nodesMap.get(x)));
               
           
               
           }
           }
  
            for(int i=0;i<Values2.size();i++){
                
           for(int x=0; x<nodesMap.size();x++){
          
               
               if(nodesMap.get(x).value.equals(Values2.get(i)))
                   structs.get(i).node2=nodesMap.get(x);
    
           }
           }
            
           for(int i=0;i<Values2.size();i++){
                
           for(int x=0; x<nodesMap.size();x++){
          
               
               if(nodesMap.get(x).value.equals(Values2.get(i)))
                   structs.get(i).cost= Double.parseDouble(Values3.get(i));
    
           }
           }
           
         
         for(int i=0;i<structs.size();i++){
              for (int x=0; x<nodesMap.size();x++){
                  
                  if(structs.get(i).node1.value.equals(nodesMap.get(x).value)){
                      nodesMap.get(x).edgs.add(
                              new Edge ( 
                                      (structs.get(i).node2), 
                                      (structs.get(i).cost))
                      );

                         
                  }
                  
                
              }
          }
           
          
          int goalIndex = 0;
          for(Node currentNode:nodesMap){
              
             int size=currentNode.edgs.size();;
             
             
             if(size ==3){
                 
             
              currentNode.adjacencies = new Edge[]{
              currentNode.edgs.get(0), currentNode.edgs.get(1), currentNode.edgs.get(2)
          };
              
              
          }
             
              if(size ==2){
                 
             
              currentNode.adjacencies = new Edge[]{
              currentNode.edgs.get(0), currentNode.edgs.get(1)
          };
              
              
          }
              
                 if(size ==1){
                 
             
              currentNode.adjacencies = new Edge[]{
              currentNode.edgs.get(0)
          };
              
              
          }
                 
                    if(size ==0){
                 
             
              currentNode.adjacencies = new Edge[]{
              
          };
              
              
          }

          }
          
          //Index of target Node
         for(int i=0;i<nodesMap.size();i++){
             
            if(nodesMap.get(i).value.equals("G"))
                goalIndex=i;
        }
 
      
                AstarSearch(nodesMap.get(0),nodesMap.get(goalIndex));

                List<Node> path = printPath(nodesMap.get(goalIndex));

                        System.out.println("Path: " + path);


        }

        public static List<Node> printPath(Node target){
                List<Node> path = new ArrayList<Node>();
        
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
        }

        public static void AstarSearch(Node source, Node goal){

                Set<Node> explored = new HashSet<Node>();

                PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                        new Comparator<Node>(){
                                 //override compare method
                 public int compare(Node i, Node j){
                    if(i.f_scores > j.f_scores){
                        return 1;
                    }

                    else if (i.f_scores < j.f_scores){
                        return -1;
                    }

                    else{
                        return 0;
                    }
                 }

                        }
                        );

               
                source.g_scores = 0;

                queue.add(source);

                boolean found = false;

                while((!queue.isEmpty())&&(!found)){

                        //the node in having the lowest f_score value
                        Node current = queue.poll();
                        System.out.println("expanded: "+ current.value);

                        explored.add(current);

                        //goal found
                        if(current.value.equals(goal.value)){
                                found = true;
                        }

                    
                        for(Edge e : current.adjacencies){
                                Node child = e.target;
                                double cost = e.cost;
                                double temp_g_scores = current.g_scores + cost;
                                double temp_f_scores = temp_g_scores + child.h_scores;


                                
                                
                                if((explored.contains(child)) && 
                                        (temp_f_scores >= child.f_scores)){
                                        continue;
                                }

                               
                                
                                else if((!queue.contains(child)) || 
                                        (temp_f_scores < child.f_scores)){

                                        child.parent = current;
                                        child.g_scores = temp_g_scores;
                                        child.f_scores = temp_f_scores;

                                        if(queue.contains(child)){
                                                queue.remove(child);
                                        }

                                        queue.add(child);

                                }

                        }

                }

        }
        public static class Node{

        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;
        public List <Edge> edgs = new ArrayList <Edge>();
        public  String firstVariable;
        public Integer secondVariable;

        public Node(String val, double hVal){
                value = val;
                h_scores = hVal;
        }

        public String toString(){
                return value;
        }

}
        
        public static class struct{
            Node node1;
            Node node2;
            double cost;
            
            public struct( Node node1){
               
                this.node1=node1;
               
                
            }
            public struct( Node node2, int cost){
               
                this.node2=node2;
               
                
            }
            
            public struct(){}
            
            
        }
        
        public static class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
                target = targetNode;
                cost = costVal;
                
        
        }
}
        
        
        // TreeNode Class
    public static class TreeNode {
        
      //Node's variables  
      public List<String> childrens = new ArrayList<String>();
      public String parent = null;
      public String letter;
      public int fFunction;
      public int heuristic;
      public int pathCostFromParent;
      public int depthLevel;
      public boolean flag = false;
      
     
      // Node constructors
       public TreeNode(){
          
      }
       
        public TreeNode(String letter, int heuristic){
          
          this.letter=letter;
          this.heuristic=heuristic;
      }
        
      public TreeNode(String letter, int heuristic, String parent){
          
          this.letter=letter;
          this.heuristic=heuristic;
          this.parent = parent;
      }
      
      public TreeNode(String parent, String letter, int pathCost){
          this.parent=parent;
          this.letter=letter;
          this.pathCostFromParent=pathCost;
          
      }
      
      public TreeNode(String letter){
          this.letter=letter;
      }
      
       public void addChildren(String child){
          this.childrens.add(child);
         
      }
       
    }
    
    public static class populateTree{
        
        
    }
        
        
        
        
        
}






