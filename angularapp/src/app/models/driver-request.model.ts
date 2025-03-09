import { Driver } from "./driver.model";
import { User } from "./user.model";

export interface DriverRequest {
    driverRequestId?:number;
    userId?:number;
    user?: User
    driverId?:number;
    driver?: Driver
    requestDate?:Date;
    status?:string;
    tripDate?:Date;
    timeSlot?:Date;
    pickupLocation?:string;
    dropLocation?:string;
    estimatedDuration?:string;
    paymentAmount?:number;
    comments?:string;
    actualDropTime?:Date;
    actualDuration?:string;
    actualDropDate?:Date;
}
