NB_LEDS = 8
ON = 1
OFF = 0
PUSH_THRESHOLD = 500

#Wheel speed variables */
#static double 
left_wheel_speed
#static double 
right_wheel_speed

# LED variables */
LED =  [0]*8

# iterator */
#i = 0 not needed

# Boolean variables */
converge = False # Moving towards the box
push = False # Standing close to the box

#/******************************
# * Internal functions
#*******************************/

def update_speed(IR_number):

	if (IR_number==0):
		left_wheel_speed = left_wheel_speed + 700
	elif(IR_number == 7):
		right_wheel_speed = right_wheel_speed + 700
	elif(IR_number == 1):
		left_wheel_speed = left_wheel_speed + 350
	elif (IR_number == 6):
		right_wheel_speed = right_wheel_speed + 350
	elif (IR_number == 2):
		left_wheel_speed = left_wheel_speed + 550
		right_wheel_speed = right_wheel_speed - 300
    elif (IR_number == 5):
        right_wheel_speed = right_wheel_speed + 550
        left_wheel_speed = left_wheel_speed - 300
    elif (IR_number == 3):
        left_wheel_speed = left_wheel_speed + 500
    elif(IR_number == 4):
        right_wheel_speed = right_wheel_speed + 500
    
#The movement for converging to the box */
def converge_to_box(IR_sensor_value, IR_threshold):
	left_wheel_speed = 0
	right_wheel_speed = 0
	for i in range(0,NB_LEDS):
		if(IR_sensor_value[i] < IR_threshold):
			LED[i] = ON
			update_speed(i)
		else
			LED[i]=OFF
			
# The behavior when pushing the box */
def push_box(IR_sensor_value, IR_threshold):
	left_wheel_speed = 0
	right_wheel_speed = 0
	# Blink for visual pushing feedback
	for i in range(0,NB_LEDS):
		if(LED[i])
			LED[i] = OFF
		else
			LED[i]=ON
		if(IR_sensor_value[i] < IR_threshold):
			update_speed(i)
	if((IR_sensor_value[0]<IR_threshold) and (IR_sensor_value[7]<IR_threshold)):
		left_wheel_speed = 1000
		right_wheel_speed = 1000
	


# Selects the behavior push or converge */
def select_behavior(IR_sensor_value):
	push = False
	converge = True
	for i in range(0,NB_LEDS):
		if (IR_sensor_value[i] < PUSH_THRESHOLD):
			push = True
		
#/******************************
# * External functions
#*******************************/

#Converge, push, and stagnation recovery */
def swarm_retrieval(IR_sensor_value, IR_threshold):
	select_behavior(IR_sensor_value)
	if(push):
		push_box(IR_sensor_value, IR_threshold)
	else: #converge
		converge_to_box(IR_sensor_value, IR_threshold)



def get_retrieval_left_wheel_speed():
{
	return left_wheel_speed
}


def get_retrieval_right_wheel_speed():
{
	return right_wheel_speed
}

#/* Returns the state (ON/OFF) of the given LED number */
def get_LED_state(LED_num):
{
	return LED[LED_num]
}