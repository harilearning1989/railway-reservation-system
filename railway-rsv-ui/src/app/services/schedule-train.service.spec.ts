import { TestBed } from '@angular/core/testing';

import { ScheduleTrainService } from './schedule-train.service';

describe('ScheduleTrainService', () => {
  let service: ScheduleTrainService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScheduleTrainService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
