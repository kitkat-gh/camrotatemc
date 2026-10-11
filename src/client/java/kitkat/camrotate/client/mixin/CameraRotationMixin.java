package kitkat.camrotate.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class CameraRotationMixin {

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(CallbackInfo ci) {
        if (minecraft == null || minecraft.player == null || minecraft.level == null) return;

        LocalPlayer player = minecraft.player;

        // Don't rotate while riding a vehicle (boat, minecart, horse, etc.)
        if (player.isPassenger()) return;

        // Sync the player's body and head yaw to match where the camera is looking
        float cameraYaw = player.getYRot();

        player.yBodyRot = cameraYaw;
        player.yBodyRotO = cameraYaw;
        player.yHeadRot = cameraYaw;
        player.yHeadRotO = cameraYaw;
    }
}