#L3_CarRental(Java Web ưith MVC)
Function 1: Login - 50 Points
o In order to shopping, an authentication is required.
o If the user has not authenticated, the system redirects to the login page.
o The actor enters userID and password, the function checks if the userID with the password is in the
available user list, then grant the access permission. If not, a message would appear no notify that user is
not found.
o Must integrated login using Google reCAPTCHA service.
o Login function is required to shopping.
- Function 2: Car Search - 50 Points
o List first 20 available items in the system order by date: car name, color, year, category, price, quantity.
Paging is required to use.
o Each car has different price.
o User can find the car based on car name or car category and rental date and return date and amount of
car.
o All users can use this function (login is not required)
- Function 3: Create new account - 50 points
o Register new user: email as ID, phone, name, address, create date.
o Create date is current date.
o The default status of new user is New.
o You need verify the email address of the user before changing the status of new user to Active.
- Function 4: Renting - 150 points
o All users can use this function excepting admin role (login is required)
o Add the selected car to cart.
o Each user can rent any available car in the list.
o User can view the selected car in the cart. For each car: car name, car type, amount, price, total. The
screen must show the total amount of money of this cart.
o User can remove the car from the cart. The confirm message will show before delete action.
o User can update amount of each car in cart.o Click the Confirm button to store the order to database (must store order date). The warning message will
show if the selected car is out of stock.
o During booking user enter the discount code (if any). Each discount code has its expiry date.
- Function 5: Order history - 100 points
o User can take over the order history: list of order sort by booking date.
o Support search function: search by name or order date
o Support delete function to delete the order (update the status of order to inactivate).
- Function 6: Feedback on the quality of car service - 50 points (Extra)
o The user sends a feedback to the quality of the car he has rented.
o Rating on a scale of: from 0 to 10.
