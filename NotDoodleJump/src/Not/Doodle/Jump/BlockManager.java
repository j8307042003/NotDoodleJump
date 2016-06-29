package Not.Doodle.Jump;

import java.util.LinkedList;
import java.util.Random;

import android.graphics.Color;
import android.util.Log;

public class BlockManager {
	
	LinkedList<GameObject> gameObj;
	LinkedList<GameObject> blocks ;
	LinkedList<GreenBlock> green ;
	LinkedList<BlueBlock> blue ;
	LinkedList<WhiteBlock> white ;
	LinkedList<RedBlock> red ;
	LinkedList<YellowBlock> yellow ;
	LinkedList<Monster> monsters;
	
	LinkedList<Double> randTable = new LinkedList<Double>( ) ;
	
	int restBlockCount = 0 ;
	
	double greenInitR = 0.5f ;
	double restInitR = 0.25f ;

	
	double densityR = 0.7f ;
	
	int decayHeight = 500 ;
	int nextDecay = 2000 ;
	int maxAddBlock = 4 ;
	
	Player player ;
	Camera cam ;
	
	float nextMonster = 2000;
	float monsterDistance = 2000 ;
	
	final float jumpHeight = (float)(Math.pow(Player.jumpSpeed, 2)/ (-2*scene.gravityConst))-20;
	int width ;	
	float highest;
	boolean addFinish = false;
	
	public BlockManager( int width, Player player, Camera cam  ) {
		this.player = player ;
		this.width = width;
		this.highest = 0;
		this.cam = cam ;
		green = new LinkedList<GreenBlock>();
		blue = new LinkedList<BlueBlock>();
		red = new LinkedList<RedBlock>();
		white = new LinkedList<WhiteBlock>() ;
		yellow = new LinkedList<YellowBlock>() ;
		monsters = new LinkedList<Monster>() ;
		
		randTable.add(this.greenInitR) ;
		randTable.add(this.restInitR) ;
		restBlockCount = 1 ;
	
		int i = 0;
		while( i < 10 ){
			green.add(new GreenBlock( 0, -100, ObjectType.Block ));
			blue.add(new BlueBlock( 0, -100, ObjectType.Block ));
			red.add(new RedBlock( 0, -100, ObjectType.Block ));
			white.add(new WhiteBlock(0, -100, ObjectType.Block) ) ;
			yellow.add(new YellowBlock(0, -100, ObjectType.Block)) ;
			green.get(i).isSleeping = true ;
			blue.get(i).isSleeping = true ;
			red.get(i).isSleeping = true ;
			white.get(i).isSleeping = true ;
			yellow.get(i).isSleeping = true ;
			i++;
		}
		
		findAllBlocks() ;
		setRandomSet( new double[]{ 0.5f, 0.5f  } ) ;
		setBlockRandomValue( 0, 0.9f ) ;
	}
	public void restart() {
		setRandomSet( new double[]{ 0.5f, 0.5f  } ) ;
		setBlockRandomValue( 0, 0.9f ) ;
		densityR = 0.7f ;
		
		decayHeight = 2000 ;
		maxAddBlock = 4 ;
		nextMonster = 2000;
		monsterDistance = 2000 ;
		restBlockCount = 1 ;
		
	}
	
	public void update() {
		findAllBlocks() ;
		collectUselessBlock() ;
		GameObject highest = getHighestBlock() ;
		GameObject tmpObj = null ;
		if ( highest == null ) { // create a block under player
			 tmpObj = getBlockFromList( green) ;
			 tmpObj.setPosition(player.getX(), (player.getY()-player.getHeight()-10)) ;
			 tmpObj.awake();
			 return ;
		}else if (  cam.getY()+ 2*scene.gameHeight > highest.getY() ){
			if ( player.getY() > nextDecay ) {
				if ( densityR >= 0.5f )
					densityR -= 0.2f;
				if ( this.maxAddBlock > 2 )
					this.maxAddBlock-- ;
				
				if ( randTable.get(0) > 0.3 ){
					setBlockRandomValue( 0,randTable.get(0)-0.1f ) ;
				}
				
				nextDecay+=decayHeight;
				if ( decayHeight >= 100 ) decayHeight *= .8f ;
				
			}
			
			tmpObj = getRandomBlock();
			if ( tmpObj != null ) { 	
				float blockX = (float)Math.random()*(scene.gameWidth - Block.blockWidth);
				float blockY = highest.y+ (float)Math.random()*jumpHeight;
				while( checkOverlap( blockX, blockY, tmpObj ) ){
					blockX = (float)Math.random()*(scene.gameWidth - Block.blockWidth);
					blockY = highest.y+ (float)Math.random()*jumpHeight;
				}// put block
				//tmpObj.setPosition((float)Math.random()*(scene.gameWidth - Block.blockWidth) ,  highest.y+ (float)Math.random()*jumpHeight);
				tmpObj.setPosition( blockX , blockY );
				tmpObj.awake() ;
				
				if ( cam.getY()+ 2*scene.gameHeight > nextMonster ) { 						//  put monster
					if ( !(tmpObj instanceof  GreenBlock)  ) {
						tmpObj = getBlockFromList( green ) ;
						tmpObj.setPosition((float)Math.random()*(scene.gameWidth - Block.blockWidth) ,  highest.y+ (float)Math.random()*jumpHeight); 
						tmpObj.awake() ;
					}
						
					Monster tmpM = getMonsterFromList() ;
					tmpM.setPosition(tmpObj.getX()+( tmpObj.getWidth()-tmpM.getWidth() )/2, tmpObj.getY()+tmpM.getHeight()) ;
					tmpM.awake() ;
					nextMonster += monsterDistance ;
					if ( monsterDistance > 500 ) monsterDistance *= 0.8 ;
				}
					
				int blockCount = 0 ;
				for ( double i = densityR*( tmpObj.getY() - highest.getY() /jumpHeight); blockCount < maxAddBlock ; i*=0.7 ) {
					if ( Math.random() < densityR ) {
						while( checkOverlap( blockX, blockY, tmpObj ) ){
							blockX = (float)Math.random()*(scene.gameWidth - Block.blockWidth);
							blockY = highest.y+ (float)Math.random()*jumpHeight;
						}
						tmpObj = getRandomBlock();
						//tmpObj.setPosition((float)Math.random()*(scene.gameWidth - Block.blockWidth) ,  highest.y+ (float)Math.random()*(3*jumpHeight/4));
						tmpObj.setPosition( blockX , blockY );
						tmpObj.awake() ;
					}else {
						break ;
					}
				}
				
			}
			
			
			
			
			
		}
		
		
		
	}
	
	
	
	
	private GameObject getHighestBlock() { // null if no block alive
		GameObject highest = null ;
		for ( int i = 0 ; i < blocks.size() ; i++ ){
			if ( blocks.get(i).isSleeping == false ){
				if ( highest == null ){
					highest = blocks.get(i);
				}
				else if ( highest.getY() < blocks.get(i).getY() )
					highest = blocks.get(i);
			}
				
		}
		
		return highest ;
		
	}
	
	
	private GameObject getRandomBlock() {
		double randomNum = Math.random() ;
		int result = 0 ;
		double sum = 0 ;
		for ( int i = 0 ; i < randTable.size() ; i++ ) {
			if ( randomNum >= sum && randomNum < sum + randTable.get(i) ){
				result = i ;
				break;
			}
			
			sum+=randTable.get(i) ;
		}
		
		if ( result >= 1 ) { // other block
			if ( player.getY() > 2000 && this.restBlockCount < 2 )
				restBlockCount = 2 ;
			else if ( player.getY() > 4000 && this.restBlockCount < 3 )
				restBlockCount = 3 ;

			
			result = (int)(( Math.random() * (restBlockCount+1) ) +1);
		}
		
		if ( result == 0 ) {
			return getBlockFromList( green ) ;
		}
		else if ( result == 1 ) {
			return getBlockFromList( blue ) ;
		} else if ( result == 2 ) {
			
			return getBlockFromList( red ) ;
		} else if ( result == 3 ) {
			return getBlockFromList( white ) ;
		} else if ( result == 4 ) {
			return getBlockFromList( yellow ) ;
		}
		
		return getBlockFromList( green ) ;
		
	}
	
	private void setRandomSet( double[] table ) {
		for ( int i = 0 ; i < this.randTable.size() ; i++ ) {
			if ( i < table.length  )
				randTable.set(i, table[i])  ;
			else break ;
		}
		
	}
	
	private void setBlockRandomValue( int i, double random ) {
		if ( i > randTable.size() ) return ;
		
		double restR = (randTable.get(i) - random)/(randTable.size()-1);
		randTable.set(i, random) ;
		for ( int j = 0 ; j < randTable.size() ; j++ ) 
			if ( i != j ) {
				randTable.set(j, randTable.get(j)+ restR);
			}
	}
	
	
	private Monster getMonsterFromList(){
		Monster tmp = null ;
		for ( int i = 0 ; i < monsters.size() ; i++ ) {
			if( monsters.get(i).isSleeping ) {
				tmp = monsters.get(i) ;
			}
		}
		
		if ( tmp == null ) {
			tmp = new Monster( 0,0, ObjectType.Monster ) ;
			tmp.isSleeping =true ;
			monsters.add(tmp);
		}
		
		return tmp ;
		
	}
	
	
	private GameObject getBlockFromList(LinkedList list ) {
		GameObject tmpObj = null ;
		tmpObj = searchBlockFromList( list ) ;
		if ( tmpObj == null && list == green ){
			tmpObj = new GreenBlock(0, -100, ObjectType.Block);
			green.add((GreenBlock) tmpObj ) ;
		}else if ( tmpObj == null && list == red ) {
			tmpObj = new RedBlock( 0, -100,  ObjectType.Block ) ;
			red.add((RedBlock)tmpObj) ;
		}else if ( tmpObj == null && list == blue ) {
			tmpObj = new BlueBlock( 0, -100, ObjectType.Block ) ;
			blue.add(( BlueBlock )tmpObj) ;
		} else if ( tmpObj == null && list == white ) {
			tmpObj = new WhiteBlock( 0, -100, ObjectType.Block ) ;
			white.add(( WhiteBlock )tmpObj) ;
		} else if ( tmpObj == null && list == yellow ) {
			tmpObj = new YellowBlock( 0, -100, ObjectType.Block ) ;
			yellow.add(( YellowBlock )tmpObj) ;
		}
		
		
		return tmpObj ;
		
	}
	
	
	
	
	private < T extends GameObject> GameObject searchBlockFromList( LinkedList<T> list ){
		T obj = null ;
		for ( int i = 0 ; i < list.size() ; i++ ){
			if ( list.get(i).isSleeping ) {
				obj = list.get(i);
				break ;
			}
		}
		
		
		
		if ( obj != null ) {
			list.remove(obj);
			list.add(obj);
		}
		
		return obj ;
	}
	
	private void collectUselessBlock() {
		for ( int i = 0 ; i < blocks.size() ; i++) {
			if ( !blocks.get(i).isSleeping && blocks.get(i).getY() < cam.getY() )
				blocks.get(i).isSleeping = true ;
		}
		
		for ( int i = 0 ; i < monsters.size() ; i++) {
			if ( !monsters.get(i).isSleeping && monsters.get(i).getY() < cam.getY() )
				monsters.get(i).isSleeping = true ;
		}
		
	}
	
	private void findAllBlocks() {
	
		blocks = new LinkedList<GameObject>();
		
		for( int i = 0 ; i < GameObject.gameObjList.size() ; i++ ){
			if ( GameObject.gameObjList.get(i) instanceof Block ) 
			blocks.add(GameObject.getGameObjects().get(i));
		}
	
		
	}
	
	private boolean checkOverlap( float blockX, float blockY, GameObject tmp ){
		int i = 0;
		while( i < blocks.size() ) {
			if( blocks.get(i).isSleeping)
				i++;
			else{
				if( blocks.get(i).getY() <= blockY + blocks.get(i).getHeight() &&
					blocks.get(i).getY() + blocks.get(i).getHeight() >= blockY ){
					if( blocks.get(i).paint.getColor() == Color.BLUE || tmp.paint.getColor() == Color.BLUE )
						return true;
					else if( blocks.get(i).getX() <= blockX + blocks.get(i).getWidth() &&
							 blocks.get(i).getX() + blocks.get(i).getWidth() >= blockX )
						return true;
				}
				
				i++;
			}
		}
		
		return false;
	}
	
}
