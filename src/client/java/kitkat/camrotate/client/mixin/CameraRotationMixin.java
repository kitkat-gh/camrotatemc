package kitkat.camrotate.client.mixin;

import kitkat.camrotate.CamrotateConfig;
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
        CamrotateConfig config = CamrotateConfig.get();

        // general > mod enabled
        if (!config.modEnabled) return;

        if (minecraft == null || minecraft.player == null || minecraft.level == null) return;

        LocalPlayer player = minecraft.player;

        // rotation > only in first person
        if (config.onlyInFirstPerson && !minecraft.options.getCameraType().isFirstPerson()) return;

        // rotation > rotation type while riding
        // normal = mc default rotation | always follow = camrotate rotation
        if (player.isPassenger() && config.ridingRotationType == CamrotateConfig.RidingRotationType.NORMAL) return;

        float cameraYaw = player.getYRot();

        player.yBodyRot = cameraYaw;
        player.yBodyRotO = cameraYaw;
        player.yHeadRot = cameraYaw;
        player.yHeadRotO = cameraYaw;
    }
}