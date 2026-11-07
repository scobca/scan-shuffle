import ApiResolver from "@/utils/ApiResolver.ts";

export class AuthResolver {
  private apiResolver = new ApiResolver("api/v1/auth");

  public async getCsrf(): Promise<void> {
    await this.apiResolver.request<void>({
      url: 'csrf',
      method: 'GET',
    });
  }
}
