import { Driver } from "./driver.model";
import { User } from "./user.model";

export interface Feedback {
    feedbackId?: number;
    feedbackText?: string;
    date?: Date;
    user?: User;
    userId?: number;
    driverId?: number;
    driver?: Driver
    category?: string;
    rating?: number;
}