import { ParentTaskDTO } from './ParentTaskDTO';
import { UserDTO } from './UserDTO';
import { ProjectDTO } from './ProjectDTO';

export class Task {
    constructor(
        public name: string,
        public parentTask: ParentTaskDTO,
        public startDate: string,
        public endDate: string,
        public priority: string,
        public status: string,
        public user: UserDTO,
        public project: ProjectDTO
    ) {}
}