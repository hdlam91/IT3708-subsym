from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *
from search import *
from retrieval import *
from stagnation import *
from copy import deepcopy
import Queue






controller = EpuckBasic()
controller.basic_setup()

previousDist = Queue.Queue(10)
prev1 = 0
counter = 0
first = 0
second  = 0
SECOND = 5
FIRST = 20 
prev10 = None
twoBox = True
while(True):
	distances = controller.get_proximities()
	distance_thresh = 200
	light_thresh = 1000

	#stagnation:
	if(previousDist.full()):
		prev10 = previousDist.get()
		
	if(counter >= 50 and twoBox):
		print "1000 iter"
		if(first <= FIRST ):
			controller.move_wheels(-1000,-1000, 0.1)
			first += 1
		elif(second <= SECOND):
			controller.move_wheels(-1000, 1000, 0.1)
			second += 1
		else:
			second = 0
			first = 0
			counter = 0
			controller.move_wheels(1,1,0.1)
			reset_retrieval_wheels()
	elif(not get_stagnation_state()):
		#retrieve:
		
		controller.led[8].set(0)
		lights = controller.get_lightValues()
		swarm_retrieval(lights, light_thresh)

		if(get_retrieval_left_wheel_speed() == 0 and get_retrieval_right_wheel_speed() == 0):
			#search
			distance_thresh = 200
			for i in range(1,8):
				controller.led[i].set(1)
			update_search_speed(controller.get_proximities(), distance_thresh)
			controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
		elif(get_retrieval_left_wheel_speed() == 1000 and get_retrieval_right_wheel_speed() == 1000):
			if(prev10 and get_close_to_box(distances, 2000)):
				valuate_pushing(distances, prev10)
			controller.move_wheels(get_retrieval_left_wheel_speed(), get_retrieval_right_wheel_speed(), 0.1)
			controller.led[0].set(1)
		#run retrieval mode
		else:
			controller.move_wheels(get_retrieval_left_wheel_speed(), get_retrieval_right_wheel_speed(), 0.1)
			controller.led[0].set(get_LED_state(0))
			counter += 1
			print "iter:" + str(counter)
			for i in range(1,8):
				controller.led[i].set(0)
			
	else: #stagnation:
		controller.led[8].set(get_green_LED_state())
		controller.led[0].set(0)
		stagnation_recovery(distances, distance_thresh)
		controller.move_wheels(get_stagnation_left_wheel_speed()*2,get_stagnation_right_wheel_speed()*2, 0.05)
		#reset_retrieval_wheels()
	if(not previousDist.full()):
		previousDist.put(deepcopy(distances))
	prev1 = deepcopy(distances)
controller.stop_moving()
