package app.dreamjournal.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.catppuccin.Flavor
import com.catppuccin.Palette

@Immutable
data class CatppuccinColorScheme(
    val rosewater: Color,
    val flamingo: Color,
    val pink: Color,
    val mauve: Color,
    val red: Color,
    val maroon: Color,
    val peach: Color,
    val yellow: Color,
    val green: Color,
    val teal: Color,
    val sky: Color,
    val sapphire: Color,
    val blue: Color,
    val lavender: Color,
    val text: Color,
    val subtext1: Color,
    val subtext0: Color,
    val overlay2: Color,
    val overlay1: Color,
    val overlay0: Color,
    val surface2: Color,
    val surface1: Color,
    val surface0: Color,
    val base: Color,
    val mantle: Color,
    val crust: Color,
)

fun Flavor.toScheme(): CatppuccinColorScheme {
    return CatppuccinColorScheme(
        rosewater = this.rosewater().toCompose(),
        flamingo = this.flamingo().toCompose(),
        pink = this.pink().toCompose(),
        mauve = this.mauve().toCompose(),
        red = this.red().toCompose(),
        maroon = this.maroon().toCompose(),
        peach = this.peach().toCompose(),
        yellow = this.yellow().toCompose(),
        green = this.green().toCompose(),
        teal = this.teal().toCompose(),
        sky = this.sky().toCompose(),
        sapphire = this.sapphire().toCompose(),
        blue = this.blue().toCompose(),
        lavender = this.lavender().toCompose(),
        text = this.text().toCompose(),
        subtext1 = this.subtext1().toCompose(),
        subtext0 = this.subtext0().toCompose(),
        overlay2 = this.overlay2().toCompose(),
        overlay1 = this.overlay1().toCompose(),
        overlay0 = this.overlay0().toCompose(),
        surface2 = this.surface2().toCompose(),
        surface1 = this.surface1().toCompose(),
        surface0 = this.surface0().toCompose(),
        base = this.base().toCompose(),
        mantle = this.mantle().toCompose(),
        crust = this.crust().toCompose(),
    )
}

val LatteColorScheme = Palette.LATTE.toScheme()
val FrappeColorScheme = Palette.FRAPPE.toScheme()
val MacchiatoColorScheme = Palette.MACCHIATO.toScheme()
val MochaColorScheme = Palette.MOCHA.toScheme()

internal val LocalCatppuccinColorScheme = staticCompositionLocalOf { MochaColorScheme }

val CatppuccinColors: CatppuccinColorScheme
    @Composable
    get() = LocalCatppuccinColorScheme.current
